import { Component, OnInit } from '@angular/core';
import { ProfileService} from '../profile.service';
import { Router } from '@angular/router';
import { UserP } from '../models/UserP';
import { environment } from '../../environments/environment';



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
    userPassword: ""
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
    
    
    // environment.errorCodes.forEach((element)=>{
    //   if(this.returnedUser.status == element) {
    //     this.incorrectLogin = true;
    //   }
    // })
    // if(this.incorrectLogin == false) {
    //   this.profileService.currentUser = this.returnedUser;
    //   this.router.navigate(['/directory']);
    // }
  }

  ngOnInit(): void {
  }

}
