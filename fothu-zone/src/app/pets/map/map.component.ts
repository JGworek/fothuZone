import { Component, OnInit } from '@angular/core';
import { ProfileService } from 'src/app/service/profile.service';
import { Map } from '../../models/Map';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {

  constructor(public profileService: ProfileService) { }

  currentMap: Map = {
    id: 1,
    name: "Mumble's Creek",
    startingRoom: 30,
    bossRoom: 89,
    nonBarrenCells: [11, 12, 13, 21, 23, 25, 26, 27, 30, 31, 32, 33, 35, 37, 38, 41, 43, 44, 45, 47, 51, 52, 57, 65, 66, 67, 73, 74, 75, 85, 86, 87, 88, 89],
  }

  truthArray: Array<boolean> = new Array<boolean>(100);
  notExploredArray: Array<boolean> = new Array<boolean>(100);
  // nonBarrenCells: Array<number> = [11, 12, 13, 21, 23, 25, 26, 27, 30, 31, 32, 33, 35, 37, 38, 41, 43, 44, 45, 47, 51, 52, 57, 65, 66, 67, 73, 74, 75, 85, 86, 87, 88, 89];
  // startingRoom: number = 30;
  // bossRoom: number = 89;
  currentRoom: number;
  petSelect: boolean = false;

  checkIfNearby(roomId: number) {
    return this.adjacentVertical(roomId) || this.adjacentHorizontal(roomId);
  }

  onSameColumn(roomId: number) {
    // Checks that the ones digit is the same
    return (this.currentRoom % 10 === roomId % 10);
  }

  onSameRow(roomId: number) {
    // Check that the tens digit is the same
    return Math.floor(this.currentRoom / 10) === Math.floor(roomId / 10);
  }

  adjacentHorizontal(roomId: number) {
    // On the same Row and their IDs are off by 1
    return this.onSameRow(roomId) && Math.abs(roomId - this.currentRoom) === 1;
  }

  adjacentVertical(roomId: number) {
    // On the same Column and their IDs are off by 10
    return this.onSameColumn(roomId) && Math.abs(roomId - this.currentRoom) === 10;
  }

  setCurrentRoom(roomId: number) {
    this.truthArray[this.currentRoom] = false;
    this.currentRoom = roomId;

    this.notExploredArray[roomId] = false;
    this.truthArray[roomId] = true;
  }

  ngOnInit(): void {
    this.notExploredArray.fill(true);
    this.truthArray.fill(false);
    this.setCurrentRoom(this.currentMap.startingRoom);
  }
}
