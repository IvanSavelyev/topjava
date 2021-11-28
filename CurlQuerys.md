Curl query's
---
### Meals
##### CURL Meals
```console
$ curl -v http://localhost:8080/topjava/rest/meals
$ curl -v http://localhost:8080/topjava/rest/meals/100008
$ curl -d '{"dateTime":"2021-06-05T15:00", "description":"updated", "calories":"999"}' -H 'Content-Type: application/json' -X PUT http://localhost:8080/topjava/rest/meals/update/100008
$ curl -X DELETE http://localhost:8080/topjava/rest/meals/delete/100008
$ curl -d '{"dateTime":"2021-06-05T15:00", "description":"created", "calories":"412"}' -H 'Content-Type: application/json' http://localhost:8080/topjava/rest/meals/create
$ curl -v http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-01&startTime=15:01&endDate=2021-01-01&endTime=21:01