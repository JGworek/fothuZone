import { Component, OnInit } from '@angular/core';
import { ProfileService } from '../service/profile.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  constructor(public profileService: ProfileService, private router: Router) { }

  ngOnInit(): void {
    if(this.profileService.currentUser.id == -1) {
      this.router.navigate(['/login']);
    }
    // this.router.navigate([{outlets: {petbar: null}}]);
  }

}
