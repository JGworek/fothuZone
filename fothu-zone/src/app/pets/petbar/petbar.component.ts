import { Component, OnInit } from '@angular/core';
import { ProfileService } from 'src/app/service/profile.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-petbar',
  templateUrl: './petbar.component.html',
  styleUrls: ['./petbar.component.css']
})
export class PetbarComponent implements OnInit {

  constructor(public profileService: ProfileService, private router: Router) { }

  goToProfile() {
    this.router.navigate([{ outlets: { primary: ['profile'], petbar: null } }]);
  }

  ngOnInit(): void {
  }

}
