import { Component, OnInit } from '@angular/core';
import { ProfileService } from 'src/app/service/profile.service';

@Component({
  selector: 'app-pets-home',
  templateUrl: './pets-home.component.html',
  styleUrls: ['./pets-home.component.css']
})
export class PetsHomeComponent implements OnInit {

  constructor(public profileService: ProfileService) { }


  ngOnInit(): void {
    if (this.profileService.currentUser.id != -1) {
      this.profileService.updateUser();
    }
  }

}
