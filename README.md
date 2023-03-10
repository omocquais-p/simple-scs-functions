# Spring Cloud Stream
- Functions with RabbitMQ

## Call the POST controller:

``` 
curl -X POST -H 'Content-Type: application/json' -d '{"name":"Olivier", "nationality":"FRA"}' http://localhost:8080/batch
```

```
curl -X POST -H 'Content-Type: application/json' -d '{"name":"Bob", "nationality":"USA"}' http://localhost:8080/batch
```