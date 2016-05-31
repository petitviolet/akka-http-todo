# akka-http-slick

### Run unit test:
```
$ ./activator test
 
 ```

### Run http server(It automatically connect with H2 database):
```
$ ./activator run

```

### Test http rest point using curl:

1) Get Todo detail by todo id

 request:
 ```
$ curl localhost:9000/todo/1
 
 ```
response:
```
 {"name":"SBI todo","id":1}
 ```

2)Get all Todo detail


 request:
```
$ curl localhost:9000/todo/all
```
response:
```
[{"name":"SBI todo","id":1},{"name":"PNB todo","id":2},{"name":"RBS todo","id":3}]
```

3)Save new todo detail

 request:
 ```
   $  curl -XPOST 'localhost:9000/todo/save'  -d '{"name":"New Todo"}'
   ```
  
 response:
 
 Todo has  been saved successfully

3)Update new todo detail

  request:
  ```
  $  curl -XPOST 'localhost:9000/todo/update'  -d '{"name":"Updated todo", "id":1}'
  
  ```
  
  response:
  
   Todo has  been updated successfully

4)delete todo by id

  request:
  
  ```
  $ curl -XPOST 'localhost:9000/todo/delete/1
  
  ```
  response:
  
  Todo has been deleted successfully
