module.exports = function(app) {


    app.get('/', function(req, res) {
        res.end("Node-Android-Chat-Project");
    });


    app.post('/login',function(req,res){

        console.log("Called!")


        res.json({name: "name"});

    });

};
