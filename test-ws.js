// execute on modern browse
var ws = new WebSocket("ws://localhost:8080/todo/ws", ['echo-protocol']);

ws.onopen = function() {
   ws.send('hello');
 };

// Log errors
ws.onerror = function (error) {
  console.log('WebSocket Error ' + error);
};

// Log messages from the server
ws.onmessage = function (e) {
  console.log('Server: ' + e.data);
};

ws.send("all");
