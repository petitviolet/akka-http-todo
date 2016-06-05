# akka-http-slick

## How to launch 

### Run http server:

```
$ ./activator run
```

### Run http server with configuration(e.g. loglevel, mysql-user, etc.):

```
$ ./activator 'run' -DLOG_LEVEL=INFO -DLOG_DIR=./logs -Dakka.loglevel=debug -DMYSQL_USER=test 
```

### Compose fat JAR

```
$ ./activator assembly
```

# API Endpoint

## ToDo

- GET: /todo/all
- GET: /todo/search?name=<name>
- GET: /todo/<id>
- POST: /todo/save
    - `Content-Type: application/json`
    - `{"name":"crazy"}`
- PUT: /todo/update/<id>
    - `Content-Type: application/json`
    - `{"name":"crazy"}`
- DELETE: /todo/delete/<id>
