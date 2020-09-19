const mongoose = require('mongoose')
const viewHistory = require('./models/viewHistory')
const searchingHistory = require('./models/searchingHistory')
const user = require('./models/user');
// #5 Change URL to your local mongodb
const url = "mongodb://localhost:27017/StackOverFlowDB";
// ===============================

mongoose.connect(url, { useNewUrlParser: true, useUnifiedTopology: true, useFindAndModify: false })

//get Data

function getAllViewHistory(req, res) {
    viewHistory.find({}, function (err, data) {
        if (err) {
            res.status(500).json({ status: "error", message: err });
        }
        res.json(data);
    });
}

function getAllSearchingHistory(req, res) {
    searchingHistory.find({}, function (err, data) {
        if (err) {
            res.status(500).json({ status: "error", message: err });
        }
        res.json(data);
    });
}

function getAllUser(req, res) {
    user.find({}, function (err, data) {
        if (err) {
            res.status(500).json({ status: "error", message: err });
        }
        res.json(data);
    });
}

function getOneUser(req, res) {
    var ID = req.params.id;
    user.findOne({_id:ID}, function (err, data) {
        if (err) {
            res.status(500).json({ status: "error", message: err });
        }
        res.json(data);
    });
}

//add Data

function addSearchingHistory(req, res) {
    var newId = new mongoose.mongo.ObjectId();
    var payload = req.body; // add _id
    payload._id = newId;
    payload.Date = new Date(payload.Date)
    console.log(payload);
    var NewSearchingHistory = new searchingHistory(payload);
    console.log(NewSearchingHistory);
    NewSearchingHistory.save(function (err) {
        if (err) {
            res.status(500).json(err);
            console.log(err);
        }
        res.json({ status: "added NewSearchingHistory" });
    });
}

function addViewHistory(req, res) {
    var newId = new mongoose.mongo.ObjectId();
    var payload = req.body;
    payload._id = newId; // add _id
    payload.Date = new Date(payload.Date)
    var NewViewHistory = new viewHistory(payload);
    // console.log(req.body.ID);a
    NewViewHistory.save(function (err) {
        if (err) {
            res.status(500).json(err);
            console.log(err);
        }
        res.json({ status: "added NewViewHistory" });
    });
}

function addUser(req, res) {
    var newId = new mongoose.mongo.ObjectId();
    var payload = req.body;
    payload._id = newId; // add _id
    
    var NewUser = new user(payload);
    console.log(NewUser);
    NewUser.save(function (err) {
        if (err) {
            res.status(500).json(err);
        } else {
            res.json({ status: "added NewUser" });
        }
    });
}


//delete Data

function deleteSearchingHistory(req, res) {
    var id = req.params.id;
    searchingHistory.findByIdAndRemove(id, function (err) {
        if (err) res.status(500).json(err);
        res.json({ status: "delete a data" });
    });
}

function deleteViewHistory(req, res) {
    var id = req.params.id;
    viewHistory.findByIdAndRemove(id, function (err) {
        if (err) res.status(500).json(err);
        res.json({ status: "delete a data" });
    });
}

function deleteUser(req, res) {
    var id = req.params.id;
    user.findByIdAndRemove(id, function (err) {
        if (err) res.status(500).json(err);
        res.json({ status: "delete a data" });
    });
    // ===============================
}

//User Management

function findUser(req, res) {
    var userID = req.params.userid;
    //check if that UserID is already exist
    user.findOne({ UserID: userID }, function (err, data) {
        console.log('sent '+userID)
        if (data != null) {
            console.log('Dupe')
            res.json(true);
        } else {
            console.log('not Dupe')
            res.json(false);
        }
    });
}

function editUser(req, res) {
    var payload = req.body
    var id = req.params.id; 
    console.log(payload) 
    user.findByIdAndUpdate(id,payload,function (err) {
        if (err) res.status(500).json(err);
        res.json({status : "updated user"});
    });
}


//Connection with plugin

function authen(req, res) {
    user.findOne({ UserID: req.body.UserID, Password: req.body.Password }, function (err, data) {
        console.log(req)
        if (err) {
            res.status(500).json({ status: "error", message: err });
        }
        console.log(data)
        if (data != null) {
            res.json("success");
        } else {
            res.json("not success");
        }
    });
}

function checkConnection(req, res) {
    status = mongoose.connection.readyState;
    // console.log(status);
    res.json(status)
    //     ready states being:
    // 0: disconnected
    // 1: connected
    // 2: connecting
    // 3: disconnecting

}

//Dashboard
function distinctTags(req,res){
    viewHistory.aggregate([
        { $unwind: "$Tags" }, 
        { $group: { "_id": "$Tags", "count": { $sum: 1 } } }, 
        { $project: { "Tags": "$_id", "count": 1 } },
        { $sort:{"count":-1}},

],        function(err,data) {
    if (err) {
        res.status(500).json({ status: "error", message: err });
    }
    res.json(data);
})}

function findViewByUser(req, res) {
    var userID = req.params.userid;
        viewHistory.find({ UserID: userID }, function (err, data) {
        if (err) {
            res.status(500).json({ status: "error", message: err });
        }
        res.json(data);
    });
}

function distinctTagsByUser(req,res){
    var userID = req.params.userid;
    viewHistory.aggregate([
        { $match: { UserID: userID } },
        { $unwind: "$Tags" }, 
        { $group: { "_id": "$Tags", "count": { $sum: 1 } } }, 
        { $project: { "Tags": "$_id", "count": 1 } },
        { $sort:{"count":-1}},

],        function(err,data) {
    if (err) {
        res.status(500).json({ status: "error", message: err });
    }
    res.json(data);
})}

function findSearchingByUser(req, res) {
    var userID = req.params.userid;
        searchingHistory.find({ UserID: userID }, function (err, data) {
        if (err) {
            res.status(500).json({ status: "error", message: err });
        }
        res.json(data);
    });
}

function viewFrequency(req,res){
    viewHistory.aggregate([
        { $group: { "_id": {
            month : {$month : "$Date"},
            year : {$year : "$Date"}
            }, "count": { $sum: 1 } } }, 
        { $sort:{"_id.month":1}}],        function(err,data) {
    if (err) {
        res.status(500).json({ status: "error", message: err });
    }
    res.json(data);
})}

function searchingFrequency(req,res){
    searchingHistory.aggregate([
        { $group: { "_id": {
            month : {$month : "$Date"},
            year : {$year : "$Date"}
            }, "count": { $sum: 1 } } }, 
        { $sort:{"_id.month":1}}],        function(err,data) {
    if (err) {
        res.status(500).json({ status: "error", message: err });
    }
    res.json(data);
})} 

module.exports = {
    //get
    getAllViewHistory: getAllViewHistory,
    getAllSearchingHistory: getAllSearchingHistory,
    getAllUser: getAllUser,
    getOneUser: getOneUser,
    //add
    addViewHistory: addViewHistory,
    addSearchingHistory: addSearchingHistory,
    addUser: addUser,
    findUser: findUser,
    editUser: editUser,
    //Delete
    deleteViewHistory: deleteViewHistory,
    deleteSearchingHistory: deleteSearchingHistory,
    deleteUser: deleteUser,
    //Connecting
    authen: authen,
    checkConnection: checkConnection,
    //Dash Board
    distinctTags:distinctTags,
    findViewByUser:findViewByUser,
    findSearchingByUser:findSearchingByUser,
    distinctTagsByUser:distinctTagsByUser,
    viewFrequency: viewFrequency,
    searchingFrequency:searchingFrequency
};