import { Component, OnInit } from '@angular/core';
import { ProfileService} from '../profile.service';
import { Router } from '@angular/router';
import { UserP } from '../models/UserP';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private profileService: ProfileService, private router: Router) { }

  incorrectLogin: boolean = false;
  loggingInUser: UserP = {
    id: 0,
    username: "",
    favoriteColor: "",
    pets: [],
    userPassword: "string"
  };

  returnedUser;

  async logIn() {
    this.incorrectLogin = false;
    let returnedPromise = await fetch(`http://ec2-54-161-212-213.compute-1.amazonaws.com:6969/users/login`, {method: 'post', body: JSON.stringify(this.loggingInUser)});
    this.returnedUser = await returnedPromise.json();
    if(this.returnedUser.status != 500) {
    this.profileService.currentUser = this.returnedUser;
    this.router.navigate(['/directory']);
    } else {
      this.incorrectLogin = true;
    }


    
    // if(this.incorrectLogin == false) {
    //   this.router.navigate(['/directory']);
    // }
  }

  ngOnInit(): void {
  }

}
