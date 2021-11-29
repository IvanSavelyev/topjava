Curl query's
---
### Meals
##### CURL Meals
```console
Get all meals for current user id
$ curl -v http://localhost:8080/topjava/rest/meals
Get meal with id 100008 and current user id
$ curl -v http://localhost:8080/topjava/rest/meals/100008
Update meal with id 100008 and current user id
$ curl -d '{"dateTime":"2021-06-05T15:00", "description":"updated", "calories":"999"}' -H 'Content-Type: application/json' -X PUT http://localhost:8080/topjava/rest/meals/100008
Delete meal with id 100008 and current user id
$ curl -X DELETE http://localhost:8080/topjava/rest/meals/100008
Create new meal for current user id
$ curl -d '{"dateTime":"2021-06-05T15:00", "description":"created", "calories":"412"}' -H 'Content-Type: application/json' http://localhost:8080/topjava/rest/meals/
Filter meals for current user id
$ curl -v http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-01&startTime=15:01&endDate=2021-01-01&endTime=21:01