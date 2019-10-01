# shorterurl-service
Most of us are familiar with seeing URLs like bit.ly or t.co on our Twitter or Facebook feeds. These are examples of shortened URLs, which are a short alias or pointer to a longer page link. For example, I can send you the shortened URL **http://bit.ly/SaaYw5** that will forward you to a very long Google URL with search results on how to iron a shirt.

# Endpoints

1.**/url**

It will accept the long url as input and convert to a shorter url. 

### For example: 

    POST "https://github.com/joeltadeu/shorterurl/blob/master/src/main/java/com/shorterurl/resource/ShorterUrlResource.java" as raw text to this endpoint. 

Endpoint will return:

    http://localhost:8080/url/tN72u2j

2.**/url/{key}**

It will extract key from the url and finds the match in redis cache to redirect to long url.

### For example: 

    GET http://localhost:8080/url/tN72u2j
    
## Build application

```shell
mvn clean package
```

## Start infrastructure

Start docker setup with grafana, prometheus, redis and the application.

```shell
docker-compose up
docker-compose down
```

## Monitoring

* Prometheus: [http://localhost:9090](http://localhost:9090)
* Grafana: [http://localhost:3000](http://localhost:3000)
    * login with admin:admin
    * setup "prometheus" data source
    * import grafana/grafana-dashboard.json
    
## Documentation (Swagger)

	http://localhost:8080/swagger-ui.html