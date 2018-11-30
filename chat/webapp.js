var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var port = process.env.PORT || 3000;
var port2 = process.env.PORT || 30000;
import { Pool, Client } from 'pg';

app.get('/chat', function(req, res){
  res.sendFile(__dirname + '/index.html');
});

app.get('/login', function(req, res){
  res.sendFile(__dirname + '/login.html');

});

io.on('connection', function(socket){
  socket.on('chat message', function(msg){
    io.emit('chat message', msg);
  });
});

http.listen(port2, function(){
  console.log('listening on *:' + port2);
});

const pool = new Pool({
  user: 'anonadmin',
  host: 'anonclass.cszu4qtoxymw.us-east-1.rds.amazonaws.com',
  database: 'mydb',
  password: 'anonpassword',
  port: 5432,
});

pool.query('SELECT * FROM dbschema.client', (err, res) => {
  console.log(err, res);
  pool.end();
});

const client = new Client({
  user: 'anonadmin',
  host: 'anonclass.cszu4qtoxymw.us-east-1.rds.amazonaws.com',
  database: 'mydb',
  password: 'anonpassword',
  port: 5432,
});
client.connect();

client.query('SELECT * FROM dbschema.client', (err, res) => {
  console.log(err, res);
  client.end();
});