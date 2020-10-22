import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { User } from '../../app/models/User';
import { UserDTO } from '../../app/models/UserDTO';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private router: Router) { }

  newUserCreation: boolean = false;
  newUser: UserDTO = {
    id: 0,
    username: "",
    favoriteColor: "",
    userPassword: "",
    secretPassword: "",
  }

  currentUser: User = {
    id: -1,
    username: "",
    favoriteColor: "",
    pets: [],
  }

  getHealthBarColor(percentNumber) {
    if (percentNumber >= 51) {
      return "progress-bar bg-success";
    } else if (percentNumber >= 26 && percentNumber < 51) {
      return "progress-bar bg-warning";
    } else if (percentNumber < 26) {
      return "progress-bar bg-danger";
    }
  }

  getUser() {
    return this.currentUser;
  }

  logout() {
    this.currentUser = {
      id: -1,
      username: "",
      favoriteColor: "",
      pets: []
    }
    if (this.router.url == "/profile") {
      this.router.navigate(["/login"]);
    }
    if (this.router.url == "/FothuPets/map(petbar:visible)") {
      this.router.navigate([{ outlets: { primary: ['FothuPets'], petbar: ['visible'] } }]);
    }
  }

  async updateUser() {
    let returnedPromise = await fetch(`${environment.fothuZoneEC2Link}/users/username/${this.currentUser.username}`);
    let returnedUser = await returnedPromise.json();
    this.currentUser = returnedUser;
  }
}
