import { Component, OnInit } from '@angular/core';

function startMap() {
  document.querySelector(".start").setAttribute("class", "start bg-warning");
  //move button to the right if there's a room
  if(document.getElementById(`${parseInt(document.querySelector(".start").id,10)+1}`).className != "barren") {
    let moveButton = document.createElement('a');
    moveButton.setAttribute("class", "btn btn-success");
    moveButton.innerText = "Move";
    moveButton.addEventListener("click", function(){
      moveHere(parseInt(document.querySelector(".start").id,10),parseInt(document.querySelector(".start").id,10)+1);
    });
    document.getElementById(`${parseInt(document.querySelector(".start").id,10)+1}`).appendChild(moveButton);
  }
  //move button to the left if there's a room
  if(document.getElementById(`${parseInt(document.querySelector(".start").id,10)-1}`).className != "barren") {
    let moveButton = document.createElement('a');
    moveButton.setAttribute("class", "btn btn-success");
    moveButton.innerText = "Move";
    moveButton.addEventListener("click", function(){
      moveHere(parseInt(document.querySelector(".start").id,10),parseInt(document.querySelector(".start").id,10)-1);
    });
    document.getElementById(`${parseInt(document.querySelector(".start").id,10)-1}`).appendChild(moveButton);
  }
  //move button to the top if there's a room
  if(document.getElementById(`${parseInt(document.querySelector(".start").id,10)-10}`).className != "barren") {
    let moveButton = document.createElement('a');
    moveButton.setAttribute("class", "btn btn-success");
    moveButton.innerText = "Move";
    moveButton.addEventListener("click", function(){
      moveHere(parseInt(document.querySelector(".start").id,10),parseInt(document.querySelector(".start").id,10)-10);
    });
    document.getElementById(`${parseInt(document.querySelector(".start").id,10)-10}`).appendChild(moveButton);
  }
  //move button to the bottom if there's a room
  if(document.getElementById(`${parseInt(document.querySelector(".start").id,10)+10}`).className != "barren") {
    let moveButton = document.createElement('a');
    moveButton.setAttribute("class", "btn btn-success");
    moveButton.innerText = "Move";
    moveButton.addEventListener("click", function(){
      moveHere(parseInt(document.querySelector(".start").id,10),parseInt(document.querySelector(".start").id,10)+10);
    });
    document.getElementById(`${parseInt(document.querySelector(".start").id,10)+10}`).appendChild(moveButton);
  }
}

function moveHere(oldId: number, newId: number) {
  document.getElementById(`${oldId}`).setAttribute("class", "");
  document.getElementById(`${newId}`).setAttribute("class", "bg-warning");
  let moveButtonElements = document.getElementsByTagName("TD");
  for (let i = 0; i < moveButtonElements.length; i++) {
    moveButtonElements[i].innerHTML = "";
  }
  //move button to the right if there's a room
  if(document.getElementById(`${newId+1}`).className != "barren") {
    let moveButton = document.createElement('a');
    moveButton.setAttribute("class", "btn btn-success");
    moveButton.innerText = "Move";
    moveButton.addEventListener("click", function(){
      moveHere(newId,(newId+1));
    });
    document.getElementById(`${newId+1}`).appendChild(moveButton);
  }
  //move button to the left if there's a room
  if(document.getElementById(`${newId-1}`).className != "barren") {
    let moveButton = document.createElement('a');
    moveButton.setAttribute("class", "btn btn-success");
    moveButton.innerText = "Move";
    moveButton.addEventListener("click", function(){
      moveHere(newId,(newId-1));
    });
    document.getElementById(`${newId-1}`).appendChild(moveButton);
  }
  //move button to the top if there's a room
  if(document.getElementById(`${newId-10}`).className != "barren") {
    let moveButton = document.createElement('a');
    moveButton.setAttribute("class", "btn btn-success");
    moveButton.innerText = "Move";
    moveButton.addEventListener("click", function(){
      moveHere(newId,(newId-10));
    });
    document.getElementById(`${newId-10}`).appendChild(moveButton);
  }
  //move button to the bottom if there's a room
  if(document.getElementById(`${newId+10}`).className != "barren") {
    let moveButton = document.createElement('a');
    moveButton.setAttribute("class", "btn btn-success");
    moveButton.innerText = "Move";
    moveButton.addEventListener("click", function(){
      moveHere(newId,(newId+10));
    });
    document.getElementById(`${newId+10}`).appendChild(moveButton);
  }
}

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
    startMap();

  }

}
