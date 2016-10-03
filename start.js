const remote = require('electron').remote;
var loadable = false;


window.onload = function(){
  var files = readDir("saves");
  loadable = files.length != 0


  if(loadable){// Saves have been found
    addButtons(["Start", "Load", "About", "Exit"]);
  }else{// No saves have been found
    addButtons(["Start", "About", "Exit"]);
  }
}



function addButtons(strings){
  var inner = "";
  for(var i = 0; i < strings.length; i++){
    inner += "<li id='"+i+"' onclick='menuClick(this.id)'>"+strings[i]+"</li>";
  }
  document.getElementById("Menu").innerHTML = inner;
}

function menuClick(id){
  if(!loadable){
    if(id == 0){// Start

    }else if(id == 1){// About
      showAbout();
    }else if(id == 2){// Exit
      remote.getCurrentWindow().close();
    }
  }else{
    if(id == 0){// Start

    }else if(id == 1){// Load
      showAbout();
    }else if(id == 2){// About

    }else if(id == 3){// Exit
      remote.getCurrentWindow().close();
    }
  }
}

function showAbout(){
  
}
