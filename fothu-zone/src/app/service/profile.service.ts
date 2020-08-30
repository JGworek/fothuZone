import { Injectable } from '@angular/core';
import { User } from '../../app/models/User';
import { UserDTO } from '../../app/models/UserDTO';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor() { }

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
}
