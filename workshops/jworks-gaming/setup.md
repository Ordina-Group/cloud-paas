= JWorks Gaming

== Business Case

=== Gaming Website

=== Showcase for JWorks capabilities

== Solution

=== Cloud Native

=== 12 Factor App

=== Service Registry

- cf cups jworks-service-registry https://jworks-service-registry.cfapps.io
- all clients: defaultZone: ${vcap.services.jworks-service-registry.credentials.uri:localhost:8761}/eureka/

=== Data API

=== First Game: Rock Paper Scissors Lizard Spock

- create application in Twitter (https://apps.twitter.com)
- ALWAYS fill in a callback URL (a placeholder is needed to be able to use real callback URLs at runtime)
- make simple yet fancy UI with https://getmdl.io/
- spring-session and spring-security: http://docs.spring.io/spring-session/docs/current-SNAPSHOT/reference/html5/guides/security.html
- implement authentication with Twitter + Spring Social
- implement authorization with Spring Security + Redis datastore

=== Front End

== Summary
