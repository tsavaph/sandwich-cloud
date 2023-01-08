# Sandwich Cloud Pet Project
#### This application is pet project based on a book Spring in Action 6 Edition

## Building
### Building Sandwich Cloud Main Application
* sandwich-cloud folder: `mvn package`
### Building Authentication Server
* sandwich-cloud\auth-server folder: `mvn package`
### Building Admin Application for adding new ingredients
* add into system host file `127.0.0.1 authserver`
* sandwich-cloud\auth-admin folder: `mvn package`

## Launching
### 1) Sandwich Cloud Pet Project
* using RabbitMQ: `java -jar sandwichcloud/target/sandwichcloud-0.0.10-SNAPSHOT.jar --spring.profiles.active=kafka`
* using Kafka: `java -jar sandwichcloud/target/sandwichcloud-0.0.10-SNAPSHOT.jar --spring.profiles.active=rabbit`
* go to `localhost:8080`
### 2) Sandwich Cloud Kitchen: used for "kitchen at restaurant" (receiving last orders by Kafka or RabbitMQ)
* using RabbitMQ: `java -jar sandwichcloud/sandwichcloud-kitchen/target/sandwichcloud-kitchen-0.0.10-SNAPSHOT.jar --spring.profiles.active=kafka`
* using Kafka: `java -jar sandwichcloud/sandwichcloud-kitchen/target/sandwichcloud-kitchen-0.0.10-SNAPSHOT.jar --spring.profiles.active=rabbit`
* go to `localhost:8888`
### 3) Authentication Server
* `java -jar sandwichcloud/auth-server/target/auth-server-0.0.1-SNAPSHOT.jar`
### 4) Admin Application for adding new ingredients
* ###### `java -jar sandwichcloud/auth-admin/target/auth-admin-0.0.1-SNAPSHOT.jar`
* ###### go to `localhost:9090`
### 5) Email Integration Application
* ###### `java -jar sandwichcloud/sandwichcloud-email/target/sandwichcloud-email-0.0.10-SNAPSHOT.jar`

Once the application is running, you can poke at the API using `curl` like this:

Fetch a list of ingredients:

----
$ curl localhost:8080/api/ingredients
----

Fetch a list of recently created sandwiches:

----
$ curl localhost:8080/api/sandwiches?recent
----

Fetch a list of orders:

----
$ curl localhost:8080/api/orders
----

Fetch a list of ingredients via the Spring Data REST-exposed API:

----
$ curl localhost:8080/data-api/ingredients
----

Fetch a list of orders via the Spring Data REST-exposed API:

----
$ curl localhost:8080/data-api/orders
----

Fetch a list of tacos via the Spring Data REST-exposed API:

----
$ curl localhost:8080/data-api/sandwiches
----

Or just fetch all resource links from the Spring Data-REST exposed API:

----
$ curl localhost:8080/data-api
----

You can also test out the Sandwich Cloud application in your browser by entering http://localhost:8080 in the browser's address bar.
As well, you can launch sandwichcloud-restclient to test functionality:

----
$ java -jar sandwichcloud-restclient\target\sandwichcloud-restclient-0.0.10-SNAPSHOT.jar
----
