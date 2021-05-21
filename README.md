# Car Diary

## Before use

You need install:

```
- jdk 16 (recommend AdoptOpenJDK)
- PostgreSQL (recommend 13)
- Node.js (recommend 16.2.0)
```

### For run frontend and backend

Inside the root directory, do a:

```
 mvn clean install
 mvn --projects backend spring-boot:run
```

Now go to http://localhost:8080/ or http://localhost:8080/swagger-ui.html

### For run frontend standalone

Inside the root directory, do a:

```
cd frontend
npm run serve
```

Now go to http://localhost:8081

### For run backend standalone

Inside the root directory, do a:

```
cd backend
mvn spring-boot:run
```

Now go to http://localhost:8080/swagger-ui.html