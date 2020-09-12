import { Injectable } from '@angular/core';
import { User } from '../../app/models/User';
import { UserDTO } from '../../app/models/UserDTO';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor() { }

  newUserCreation: boolean = false;
  newUser: UserDTO = {
    id: 0,
    username: "",
    favoriteColor: "",
    userPassword: "",
    secretPassword: "",
  }

  currentUser: User = {id: -1,
    username: "",
    favoriteColor: "",
    pets: [],
  }

  getHealthBarColor(percentNumber) {
    console.log(percentNumber);
    if(percentNumber >= 51) {
      return "progress-bar bg-success";
    } else if(percentNumber >= 26 && percentNumber < 51) {
      return "progress-bar bg-warning";
    } else if(percentNumber < 26) {
      return "progress-bar bg-danger";
    }
  }

  getUser() {
    return this.currentUser;
  }

  logout() {
    this.currentUser = {id: -1,
      username: "",
      favoriteColor: "",
      pets: []
    }
  }

  async updateUser() {
    let returnedPromise = await fetch(`http://ec2-54-161-212-213.compute-1.amazonaws.com:6969/users/username/${this.currentUser.username}`);
    let returnedUser = await returnedPromise.json();
    this.currentUser = returnedUser;
  }
}
