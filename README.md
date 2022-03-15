# Document Analyzer

## How to run the app
You should run the app and the database with the docker compose.
```
docker-compose build
docker-compose up
```

To insert the scripts run this command.
```
cat ./scripts.sql | docker exec -i postgres psql -U postgres
```


After this, you are ready tu run the spring app.

http://localhost:8080/
