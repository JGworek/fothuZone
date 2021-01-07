import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/service/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-petbar',
  templateUrl: './petbar.component.html',
  styleUrls: ['./petbar.component.css']
})
export class PetbarComponent implements OnInit {

  constructor(public userService: UserService, private router: Router) { }

  goToProfile() {
    this.router.navigate([{ outlets: { primary: ['profile'], petbar: null, battles: null } }]);
  }

  ngOnInit(): void {
  }

}