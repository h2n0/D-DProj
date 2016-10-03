var fs = require("fs");

function readFile(path){
  fs.readFile(path, "utf-8", function(err, data){
    if(err){
      console.error("Error reading file!");
      console.error(err);
      return;
    }
    console.log("Content: " + data);
  });
}

function readDir(path){
  return fs.readdirSync(path);
}

function mkDir(path){
  fs.mkdir(path, function(err){
    if(err.code == 'EEXIST'){
      return;
    }else{
      console.log(err);
    }
  });
}
