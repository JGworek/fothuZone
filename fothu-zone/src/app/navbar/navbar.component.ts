import { Component, OnInit } from '@angular/core';
import { ProfileService} from '../profile.service';
import { User } from '../models/User';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(public profileService: ProfileService) { }
  user: User;

  ngOnInit() {
  }

  //ngOnDestroy() {}

}
