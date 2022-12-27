== Building

----
$ ./mvnw package
----

== Running

After building:

----
$ java -jar tacocloud/target/taco-cloud-0.0.8-SNAPSHOT.jar
----

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
$ java -jar sandwichcloud-restclient\target\sandwichcloud-restclient-0.0.8-SNAPSHOT.jar
----
