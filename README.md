# Rest API to manage a Spacecraft Database


## Overview
The following repo contains an example of an Spring Boot application that exposes a rest api
to manage a spacecraft database.

## Guidelines

1. Clone this repository

2. Go to the root directory and compile with the command

```shell
	mvn clean install
```

3. Start the app with the next command:

```shell
	mvn spring-boot:run
```

4. Once the app starts open a browser and write:

```text
	http://localhost:8080/swagger-ui.html
```

5. The api is secured by http basic authentication. There are three users:

   - Normal user with username **user** and password **user**.
   - Swagger user with username **swagger** and password **swagger**.
   - Admin user with username **admin** and **password** admin.


6. The application can be deployed in a docker container. To build de docker image run

````shell
docker build -t spacecraft-app .
````

7. Run the container

````shell
docker run -p 8080:8080 spacecraft-app
````

After run docker container open swagger in a browser like in step 4.

The initial state of the database is

````json
{
    "page": 0,
    "size": 10,
    "totalPages": 1,
    "totalElements": 10,
    "content": [
      {"id": 1,"name": "Millennium Falcon","movie": "Star Wars","pilot": "Han Solo"},
      {"id": 2,"name": "x-wing","movie": "Star Wars","pilot": "Luke Skywalker"},
      {"id": 3,"name": "Star Destroyer","movie": "Star Wars","pilot": "Imperial troopers"},
      {"id": 4,"name": "TIE Fighter","movie": "Star Wars","pilot": "Imperial troopers"},
      {"id": 5,"name": "USS Enterprise","movie": "Star Trek","pilot": "James T. Kirk"},
      {"id": 6,"name": "Nostromo","movie": "Alien","pilot": "Dallas"},
      {"id": 7, "name": "Sulaco", "movie": "Alien"},
      {"id": 8, "name": "Spinner", "movie": "Blade Runner", "pilot": "Rick Decarad"},
      {"id": 9, "name": "Endurance", "movie": "Interstellar", "pilot": "Cooper"},
      {"id": 10, "name": "Machine", "movie": "Contact","pilot": "Ellie Arroway"}
    ]
  }
````

## Source Code Review

The app is developed in a three layers architecture described below:

1. Controller Layer: In this layer there is a SpacecraftController with methods to get, post, put, patch and delete spacecrafts

```java
@GetMapping(produces = APPLICATION_JSON_VALUE)
public ResponseEntity<PageResponse<SpacecraftResponse>> getAllSpacecraft(@RequestParam Integer page, @RequestParam Integer size);

@GetMapping(path = "/name", produces = APPLICATION_JSON_VALUE)
public ResponseEntity<List<SpacecraftResponse>> getContainsNameSpacecraft(@RequestParam String filter);

@GetMapping(path = "/{spacecraftId}", produces = APPLICATION_JSON_VALUE)
public ResponseEntity<SpacecraftResponse> getSpacecraftById(@PathVariable Long spacecraftId);

@PostMapping(produces = APPLICATION_JSON_VALUE)
public ResponseEntity<SpacecraftResponse> postSpacecraft(@RequestBody SpacecraftRequest spacecraftRequest);

@PutMapping(path="/{spacecraftId}", produces = APPLICATION_JSON_VALUE)
public ResponseEntity<SpacecraftResponse> putSpacecraft(@PathVariable Long spacecraftId, @RequestBody SpacecraftRequest spacecraftRequest);

@PatchMapping(path="/{spacecraftId}", produces = APPLICATION_JSON_VALUE)
public ResponseEntity<SpacecraftResponse> patchSpacecraft(@PathVariable Long spacecraftId, @RequestBody SpacecraftRequest spacecraftRequest);

@DeleteMapping(path="/{spacecraftId}", produces = APPLICATION_JSON_VALUE)
public ResponseEntity<Void> patchSpacecraft(@PathVariable Long spacecraftId);
```

2. Service Layer: This is the business layer that is invoked by controller layer

```java
public SpacecraftResponse findSpacecraftById(Long spacecraftId);

public PageResponse<SpacecraftResponse> allSpacecraftPaginated(Integer page, Integer size);

public List<SpacecraftResponse> containsName(String name);

public SpacecraftResponse createSpacecraft(SpacecraftRequest spacecraftRequest);

public SpacecraftResponse updateSpacecraft(Long spacecraftId, SpacecraftRequest spacecraftRequest);

public SpacecraftResponse partialUpdateSpacecraft(Long spacecraftId, SpacecraftRequest spacecraftRequest);

public void deleteSpacecraft(Long spacecraftId);
```

3. The data layer access to database directly using SpacecraftRepository

```java
public interface SpacecraftRepository extends JpaRepository<Spacecraft, Long> {
    List<Spacecraft> findByNameContaining(String name);
}
```

## Technologies used

1. Java 21
2. Spring Boot 3.3.0
3. Open API 2.5.0
4. JUnit 5.10.2
5. H2 database 2.2.224
6. Maven 3.9.6

## Disclaimers
* This is an app for training purposes only.
* Its possible that the repo is not actively maintained.

## Support
Please enter an issue in the repo for any questions or problems.
<br> Alternatively, please contact us at pabloramosdev@gmail.com
