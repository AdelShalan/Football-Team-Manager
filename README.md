# Football-Team-Manager
A practice spring boot project built upon the Microservice architecture.

In this practice project I aim to implement a fully working web application using some of the latest java back-end development technologies and libraries. 
Here I'm exposing a set of secure API endpoints, for the user to control the system, below is a list of all available endpoints + a brief description.

***Autherntication Service***
  1. /login
  2. /register

***Player Service***
  1. /createPlayer
  2. /getPlayer
  3. /offer/viewOffers
  4. /offer/acceptOffer: (if eligible, forwards a request to contract service to create a new contract between the player and the team)

***Manager Service***
  1. /createManager
  2. /assignManagerTeam: (forwards a request to the team service's assignTeamManager)
  3. /getManagerTeam
  4. /sendOffer: (produces a kafka event in the kafka topic "offers")

***Team Service***
  1. /createTeam
  2. /assignTeamManager
  3. /{teamId}/info: (retrives team's name, id, manager entity, & player entities)
  
***Contract Service***: (a contract is an explicit relationship between a player and a team)
  1. /createContract
  2. /getTeamPlayers

Implemented technologies in this project:
  1. MicroService Architecture
  2. CRUD operations using Spring data JPA and H2 in-memory database
  3. Service discovery using Eureka server/client
  4. Spring cloud gateway/routing + security filter
  5. Circuit breaker/fallback methods using Resillence4j
  6. Spring security & JWTs
  7. Remote config server (github repo)
  8. API documentation using Swagger 2.0
  9. Event streaming and processing using Apache kafka and flink
  10. Testing with Junit & mockito
  
  
P.S. This is work in progress. Suggestions and feedback are always welcome ^^
