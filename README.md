curl -X POST http://localhost:8080/counter-api/search -H "Authorization: Basic b3B0dXM6Y2FuZGlkYXRlcw==" -H "content-type: application/json" -H "Accept: application/json" -d "{\"searchText\":[\"Duis\",\"Sed\",\"Donec\",\"Augue\",\"Pellentesque\",\"123\"]}"


curl -X POST http://localhost:8080/counter-api/top/20 -H "authorization: Basic b3B0dXM6Y2FuZGlkYXRlcw==" -H "Accept: text/csv"