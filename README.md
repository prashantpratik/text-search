# Restful service to search words

# NOTE: The program has been written using Java8.

Invoke the program as below
---
Import the project on Intellij or Eclipse
Start the Spring boot project
---
# Run
----
Two end points can be trigger by using curl command or using any other client like POSTMAN

1. Search: curl -X POST http://localhost:8080/counter-api/search -H "Authorization: Basic b3B0dXM6Y2FuZGlkYXRlcw==" -H "content-type: application/json" -H "Accept: application/json" -d "{\"searchText\":[\"Duis\",\"Sed\",\"Donec\",\"Augue\",\"Pellentesque\",\"123\"]}"
2. Top:    curl http://localhost:8080/counter-api/top/20 -H "authorization: Basic b3B0dXM6Y2FuZGlkYXRlcw==" -H "Accept: text/csv"
----

Details of the problem can be found in `PROBLEM.md` file.

# Build
To build the project, run command `mvn clean package`.
The built JAR can be found in `./target/` directory.

# Test
Building with `mvn clean package` runs the unit tests.
