var express = require('express');
var fs = require("fs");

var router = express.Router();

temp = 20;

/* GET home page. */
router.get('/', function(req, res, next) {
  fs.writeFileSync("./connected.txt", "false");
  res.sendfile('./ACwebsite.html', {root: __dirname })
});

router.post('/sendtemp', function(req, res, next) {
  temp = req.body["temp"];

  fs.writeFileSync("./input.txt", temp.toString());
  console.log("Temp!!: " + temp);
  res.json({});
});

router.post('/connected', function(req, res, next) {
  fs.writeFileSync("./connected.txt", "true");
  res.json({});
});

router.get('/isconnected', function(req, res, next) {
  fs.readFileSync('./connected.txt').toString().split('\n').forEach(function (line) {
    console.log("Line: " + line);
      res.send(line);
  });
});

router.get('/whattemp', function(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");

  fs.readFileSync('./input.txt').toString().split('\n').forEach(function (line) {

    res.send(line);
    //console.log(line);
   // fs.appendFileSync("./output.txt", line.toString() + "\n");
  });

 // res.send(temp + "a");
});

router.get('/read', function(req, res, next) {
  var counter = 0;
  fs.readFileSync('./input.txt').toString().split('\n').forEach(function (line) {
    console.log(line);
    fs.writeFileSync("./output.txt", line.toString() + "\n");
  });
  res.send("blabla" );
});

module.exports = router;
