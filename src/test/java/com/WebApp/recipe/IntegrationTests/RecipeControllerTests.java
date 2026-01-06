package com.WebApp.recipe.IntegrationTests;

import com.WebApp.recipe.DTOs.UserDTOs.UserRequest;
import com.WebApp.recipe.Entities.UserEntities.User;
import com.WebApp.recipe.Repositories.UserRepositories.UserRepository;
import com.WebApp.recipe.DTOs.IngredientDTOs.IngredientRequest;
import com.WebApp.recipe.DTOs.Mapper;
import com.WebApp.recipe.DTOs.RecipeDTOs.FavRequest;
import com.WebApp.recipe.DTOs.RecipeDTOs.RecipeRequest;
import com.WebApp.recipe.DTOs.RecipeDTOs.RecipeResponse;
import com.WebApp.recipe.Entities.RecipeEntities.Recipe;
import com.WebApp.recipe.Repositories.RecipeRepositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecipeControllerTests {

    @Container
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:5.7.34"));

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate restTemplate = new TestRestTemplate();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    HttpHeaders headersWithSession;
    Mapper mapper = new Mapper();

    int savedUserId;
    int savedRecipeId;

    @BeforeEach
    void setupSessionID() {
        recipeRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User(true, "test", new BCryptPasswordEncoder(12).encode("test"));

        User savedUser = userRepository.save(user);
        savedUserId = savedUser.getId();

        UserRequest userRequest = new UserRequest("test","test", "test");

        String loginURL = "http://localhost:" + port + "/signin";

        List<String> cookies = restTemplate.postForEntity(loginURL, userRequest, String.class)
                .getHeaders()
                .get("Set-Cookie");

        headersWithSession = new HttpHeaders();
        headersWithSession.put(HttpHeaders.COOKIE, cookies);

        RecipeRequest recipeRequest = new RecipeRequest(
                "before test",
                "test",
                "this is a before test recipe",
                "here are before test steps",
                List.of(new IngredientRequest(
                        "before test",
                        "before test ingredient",
                        "before test category",
                        5.0,
                        "l")),
                "");
        Recipe recipe = mapper.toRecipe(recipeRequest);
        recipe.setUser(savedUser);

        recipe = recipeRepository.save(recipe);
        savedRecipeId = recipe.getId();
    }

    @Test
    void addRecipeTestWithSessionID() {

        String url = "http://localhost:" + port + "/custom/addRecipe";


        RecipeRequest recipeRequest = new RecipeRequest(
                "test",
                "test",
                "this is a test recipe",
                "here are test steps",
                List.of(new IngredientRequest(
                        "test",
                        "test ingredient",
                        "test category",
                        4.0,
                        "g")),
                "");
        HttpEntity<RecipeRequest> request = new HttpEntity<>(recipeRequest, headersWithSession);

        ResponseEntity<RecipeResponse> response = restTemplate
                .exchange(url, HttpMethod.POST, request, RecipeResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("test", response.getBody().getUsername());
    }

    @Test
    void getRecipeByIdTest() {

        String url = "http://localhost:" + port + "/custom/recipe/" + savedRecipeId;
        String badId = "http://localhost:" + port + "/custom/recipe/" + savedRecipeId + 100;

        HttpEntity<RecipeRequest> request = new HttpEntity<>(headersWithSession);

        ResponseEntity<RecipeResponse> response = restTemplate
                .exchange(url, HttpMethod.GET, request, RecipeResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        response = restTemplate
                .exchange(badId, HttpMethod.GET, request, RecipeResponse.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getAllRecipesTest() {
        String url = "http://localhost:" + port + "/custom/recipes";

        HttpEntity<List<RecipeResponse>> request = new HttpEntity<>(headersWithSession);

        ResponseEntity<List<RecipeResponse>> response = restTemplate
                .exchange(url, HttpMethod.GET, request,
                        new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getFavRecipesTest() {
        String url = "http://localhost:" + port + "/custom/" + savedUserId + "/favRecipes";

        HttpEntity<List<RecipeResponse>> request = new HttpEntity<>(headersWithSession);

        ResponseEntity<List<RecipeResponse>> response = restTemplate
                .exchange(url, HttpMethod.GET, request,
                        new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getRecipesByUserIdTest() {
        String url = "http://localhost:" + port + "/custom/recipes/" + savedUserId;

        HttpEntity<List<RecipeResponse>> request = new HttpEntity<>(headersWithSession);

        ResponseEntity<List<RecipeResponse>> response = restTemplate
                .exchange(url, HttpMethod.GET, request,
                        new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void favRecipeTest() {
        String url = "http://localhost:" + port + "/custom/fav";

        FavRequest favRequest = new FavRequest(savedRecipeId, false);
        HttpEntity<FavRequest> request = new HttpEntity<>(favRequest, headersWithSession);

        ResponseEntity<RecipeResponse> response = restTemplate
                .exchange(url, HttpMethod.PUT, request,
                        RecipeResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void updateRecipeTest() {
        String url = "http://localhost:" + port + "/custom/recipes/" + savedRecipeId;

        RecipeRequest recipeUpdateRequest = new RecipeRequest(
                "updated test",
                "test",
                "this is a before test recipe",
                "here are before test steps",
                List.of(new IngredientRequest(
                        "before test",
                        "before test ingredient",
                        "before test category",
                        5.0,
                        "l")),
                "");

        HttpEntity<RecipeRequest> request = new HttpEntity<>(recipeUpdateRequest, headersWithSession);

        ResponseEntity<RecipeResponse> response = restTemplate
                .exchange(url, HttpMethod.PUT, request, RecipeResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("updated test", response.getBody().getTitle());
    }

    @Test
    void deleteRecipeTest() {
        String url = "http://localhost:" + port + "/custom/deleteRecipe/" + savedRecipeId;

        HttpEntity<RecipeRequest> request = new HttpEntity<>(headersWithSession);

        ResponseEntity<RecipeResponse> response = restTemplate
                .exchange(url, HttpMethod.PUT, request, RecipeResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        Optional<Recipe> recipeFromDBOpt = recipeRepository.findById(savedRecipeId);

        Recipe recipeFromDB;
        if (recipeFromDBOpt.isPresent()) {
            recipeFromDB = recipeFromDBOpt.get();
            assertTrue(recipeFromDB.getIsDeleted());
        }

    }

}
