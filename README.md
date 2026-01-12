# Recipe web-application backend (wersja po polsku)

To jest backend mojego pet-projectu - aplikacji webowej z przepisami. 

Zapewnia następujące funkcjonalności:

- Wzorzec Controller-Service-Repository

- Rejestracja z potwierdzeniem adresu email

- Metoda wysyłająca emaile nasłuchuje na transakcje. Email jest wysyłany tylko w przypadku zakończonej transakcji

- Użyte są migracje bazodanowe

- Uwierzytelnianie z użyciem sesji HTTP

- Walidacja danych z frontendu przed zapisaniem ich do bazy

- Obsługa logiki i komunikacja z bazą danych w przypadkach odczytu, edycji, usuwania, lajkowania i dislajkowania


## Endpoints

#### Auth

```http
POST /signup - rejestracja użytkownika ({username, password})
POST /signin - logowanie użytkownika ({username, password})
POST /logout - wylogowanie ({})
```
#### Weryfikacja adresu email

```http
POST /verify - weryfikacja kodu ({email, verificationCode})
POST /resendVerificationCode - ponowne wysłanie kodu ({email})
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
1. Stwórz albo wypełnij plik z rozszerzeniem .env:
````
MAIL_PASSWORD= your email password generated via App passwords
MAIL_USER=your email

DB_URL= database url
DB_USER= database username
DB_PASSWORD= database password
````
Te zmienne są potem wykorzystywane w application.properties.
2. Ustaw ten plik jako źródło zmiennych środowiskowych
3. W projekcie użyte są migracje bazodanowe, więc wystarczy, że w pliku z rozszerzeniem .env podasz odpowiedni URL
bazy danych, nazwę użytkownika oraz hasło (czasami jest wymagana edycja uprawnień). Skrypt migracji zrobi wszystko sam :)
4. Wygeneruj App Password, żeby umożliwić wysyłanie emaili (więcej info znajdziesz tu https://support.google.com/mail/answer/185833?hl=en)

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

## Testy
Aplikacja posiada zestaw testów integracyjnych do sprawdzenia działania komunikacji pomiędzy wszystkimi warstwami
kontroler <-> serwis <-> repo <-> baza danych.

W tym celu wykorzystano test-kontenery.

## Dalsze kroki
- Możliwość dodawania obrazów do przepisów.
- Folder przepisów, które użytkownik spróbował przygotować z możliwością dodania własnych komentarzy
  dotyczących czasu, smaku itd.
- Sortowanie według składników.
- Możliwość dodawania filmików z innych źródeł niż YouTube
- Implementacja testów jednostkowych

## Autor

- [@atsiarnouskaya](https://github.com/atsiarnouskaya)




# Recipe web-application backend (in English)

This is a backend part of my pet-project Recipe web-application. 

It provides the following functionality:

- Controller-Service-Repository architectural pattern

- User registration with email verification

- Email sending is transaction-aware - emails are sent only after a successful transaction commit

- Database migrations

- Authentication based on HTTP sessions

- Validation of frontend data before persisting it to the database

- Business logic and database communication for reading, updating, deleting, liking, and disliking recipes

## Endpoints

#### Auth

```http
POST /signup - signing up ({username, password})
POST /signin - signing in ({username, password})
POST /logout - loging out ({})
```
#### Email Verification

```http
POST /verify - verify email address({email, verificationCode})
POST /resendVerificationCode - resend verification code ({email})
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

1. Create or fill in a .env file:
````
MAIL_PASSWORD=your email app password
MAIL_USER=your email address

DB_URL=database url
DB_USER=database username
DB_PASSWORD=database password
````
These variables are later used in application.properties.
2. Set this file as the source of environment variables.

3. The project uses database migrations, so providing the correct database URL, username, and password is sufficient
(sometimes adjusting database permissions is required).
The migration scripts handle the rest automatically.

4. Generate an App Password to enable email sending
(more information here: https://support.google.com/mail/answer/185833?hl=en
)

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
All other endpoints require authentication.

## Testing
The application includes a set of integration tests verifying communication between all layers:

controller <-> service <-> repository <-> database

Testcontainers are used for this purpose.

## Future Improvements
- Ability to upload images for recipes

- A folder for recipes the user has already tried, with personal notes
(e.g. preparation time, taste, improvements)

- Ingredient-based sorting and filtering

- Ability to attach videos from sources other than YouTube

- Implementation of unit tests

## Author

- [@atsiarnouskaya](https://github.com/atsiarnouskaya)

