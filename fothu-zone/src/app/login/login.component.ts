import { Component, OnInit } from '@angular/core';
import { ProfileService } from '../service/profile.service';
import { Router } from '@angular/router';
import { UserDTO } from '../models/UserDTO';
import { environment } from '../../environments/environment';



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(public profileService: ProfileService, private router: Router) { }

  incorrectLogin: boolean = false;
  samePassword: string;
  loggingInUser: UserDTO = {
    id: 0,
    username: "",
    favoriteColor: "",
    userPassword: "",
    secretPassword: ""
  };

  newUser: UserDTO = {
    id: 0,
    username: "",
    favoriteColor: "",
    userPassword: "",
    secretPassword: ""
  }

  async logIn() {
    this.incorrectLogin = false;
    let returnedPromise = await fetch(`${environment.fothuZoneEC2Link}/users/login`, { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(this.loggingInUser) });
    let returnedUser = await returnedPromise.json();
    if (returnedPromise.status.toString()[0] == '1' || returnedPromise.status.toString()[0] == '4' || returnedPromise.status.toString()[0] == '5') {
      this.incorrectLogin = true;
    } else {
      this.profileService.currentUser = returnedUser;
      this.router.navigate(['/directory']);
    }
  }

  ngOnInit(): void {
  }

}
