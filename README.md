# Recipe web-application backend (wersja po polsku)

To jest backend mojego pet-projectu — aplikacji webowej z przepisami. Zapewnia następujące funkcjonalności:

- Uwierzytelnianie z użyciem sesji HTTP

- Walidację danych z frontendu przed zapisaniem ich do bazy

- Wzorzec Controller–Service–Repository

- Edytowanie i usuwanie wyłącznie własnych przepisów (jeśli nie jesteś autorem, nie możesz ich edytować ani usuwać)

- Lajkowanie i dislajkowanie

- Stronę konta z przepisami, które dodałeś oraz które polubiłeś


## Endpoints

#### Auth

```http
POST /signup - rejestracja użytkownika ({username, password})
POST /signin - logowanie użytkownika ({username, password})
POST /logout - wylogowanie ({})
```

#### Recipes

```http
GET /custom/recipes - pobranie wszystkich przepisów z bazy
GET /custom/recipe/${id} - pobranie przepisu o id == id
GET /custom/recipes/${userId} - pobranie przepisów użytkownika o id == userId
GET /categories - pobranie wszystkich kategorii z bazy
GET /custom/${userId}/favRecipes - pobranie ulubionych przepisów użytkownika o id == userId
POST /custom/addRecipe - dodanie nowego przepisu ({recipe object})
PUT /custom/deleteRecipe/${id} - miękkie usunięcie przepisu (zmiana flagi w bazie) o id == id
PUT /custom/recipes/${id} - aktualizacja przepisu o id == id
PUT /custom/fav - polubienie lub usunięcie polubienia przepisu ({recipeId, mode (like or dislike)})
```

## Uruchamianie lokalnie

Przed uruchomieniem projektu upewnij się, że baza danych jest już uruchomiona. Schemat bazy danych znajduje się w pliku:
recipe-web-app/database-set-up/create-schema.sql
Jest to schemat bazy MySQL. Uruchom go, a następnie dodaj swoje dane logowania w application.properties:
```
#spring.datasource.url= address bazy (np. jdbc:mysql://127.0.0.1:3306/recipes)
#spring.datasource.username= twoja nazwa użytkownika
#spring.datasource.password= twoje hasło
```
Sklonuj projekt:

```bash
https://github.com/atsiarnouskaya/recipe-web-app.git
```
Zbuduj i uruchom projekt używając swojego IDE.

Aplikacja będzie działała pod adresem http://localhost:8080.
Możesz sprawdzić publiczne endpointy w Postmanie:

```http
POST /signup - signing up ({username, password})
POST /signin - signing in ({username, password})
```
Wszystkie pozostałe endpointy wymagają zalogowania.

## Autor

- [@atsiarnouskaya](https://github.com/atsiarnouskaya)




# Recipe web-application backend (in English)

This is a backend part of my pet-project Recipe web-application. It provides with functionalities as:



- Authentication using HTTP sessions
- Validating data from frontend before adding it to a db
- Controller-Service-Repository pattern used
- Editing or deleting only your own recipes! If you are not the author you won't be able to edit or delete a recipe
- Liking and disliking
- Your account page with recipes you have added and liked


## Endpoints

#### Auth

```http
POST /signup - signing up ({username, password})
POST /signin - signing in ({username, password})
POST /logout - loging out ({})
```

#### Recipes

```http
  GET /custom/recipes - get all recipes from a db
  GET /custom/recipe/${id} - get recipe with id == id
  GET /custom/recipes/${userId} - get users with id == userId recipes
  GET /categories - get all categories from a db
  GET /custom/${userId}/favRecipes - get users with id == userId favourite recipes
  POST /custom/addRecipe - add a new recipe ({recipe object})
  PUT /custom/deleteRecipe/${id} - soft delete (change a flag in a db) of recipe with id == id
  PUT /custom/recipes/${id} - updating a recipe with id == id
  PUT /custom/fav - liking or disliking a recipe ({recipeId, mode (like or dislike)})
```

## Run Locally

Before running this project please ensure you have a db running. Database scheme for this project is in a recipe-web-app\database-set-up\create-schema.sql
It is a MySQL database scheme, please run in and add your credentials in application properties:

```
#spring.datasource.url= database url (ex. jdbc:mysql://127.0.0.1:3306/recipes)
#spring.datasource.username= your username
#spring.datasource.password= your password
```
Clone the project

```bash
  git clone https://github.com/atsiarnouskaya/recipe-web-app-front.git
```
Build and start it using your IDE.

The app will be running at http://localhost:8080. You can check public endpoints in postman:

```http
POST /signup - signing up ({username, password})
POST /signin - signing in ({username, password})
```
All other endpoints assume you are logged in.

## Author

- [@atsiarnouskaya](https://github.com/atsiarnouskaya)

