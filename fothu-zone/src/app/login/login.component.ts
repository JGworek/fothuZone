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

  constructor(private profileService: ProfileService, private router: Router) { }

  incorrectLogin: boolean = false;
  loggingInUser: UserDTO = {
    id: 0,
    username: "",
    favoriteColor: "",
    userPassword: "",
    secretPassword: "",
  };

  returnedUser: any;

  async logIn() {
    this.incorrectLogin = false;
    let returnedPromise = await fetch(`http://ec2-54-161-212-213.compute-1.amazonaws.com:6969/users/login`, {method: 'POST', headers: {'Content-Type': 'application/json'}, body: JSON.stringify(this.loggingInUser)});
    this.returnedUser = await returnedPromise.json();
    if(returnedPromise.status.toString()[0] == '1' || returnedPromise.status.toString()[0] == '4' || returnedPromise.status.toString()[0] == '5') {
      this.incorrectLogin = true;
    } else {
      this.profileService.currentUser = this.returnedUser;
      this.router.navigate(['/directory']);
    }
  }

  ngOnInit(): void {
  }

}
