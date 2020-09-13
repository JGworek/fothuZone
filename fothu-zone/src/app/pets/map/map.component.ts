import { Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {

  constructor() { }

  truthArray: Array<boolean> = [false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false];
  notExploredArray: Array<boolean> = [true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true];
  startingRoom: number = 30;
  currentRoom: number;

  checkIfNearby(roomId: number) {
    if(this.truthArray[roomId+1] === true || this.truthArray[roomId-1] === true || this.truthArray[roomId+10] === true || this.truthArray[roomId-10] === true) {
      if(roomId%10 == 0 && this.currentRoom%10 == 9) {
        return false;
      }
      if(roomId%10 == 9 && this.currentRoom%10 == 0) {
        return false;
      }
      if(roomId<10 && this.currentRoom>89) {
        return false;
      }
      if(roomId>89 && this.currentRoom<10) {
        return false;
      }
      return true;
    } else {
      return false;
    }
  }

  setCurrentRoom(roomId: number) {
    // console.log(roomId);
    console.log(this.notExploredArray.length);
    this.currentRoom = roomId;
    for(let i = 0; i<this.truthArray.length; i++) {
      this.truthArray[i] = false;
    }
    this.notExploredArray[roomId] = false;
    this.truthArray[roomId] = true;
  }

  ngOnInit(): void {
    this.setCurrentRoom(this.startingRoom);
  }
}
