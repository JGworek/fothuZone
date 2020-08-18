import { Injectable } from '@angular/core';
import { User } from './models/User';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor() { }

  currentUser: User = {id: -1,
    username: "",
    favoriteColor: "",
    pets: []
  }

  getUser() {
    return this.currentUser;
  }
}
