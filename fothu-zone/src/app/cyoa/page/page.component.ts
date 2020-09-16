import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-page',
  templateUrl: './page.component.html',
  styleUrls: ['./page.component.css']
})
export class PageComponent implements OnInit {

  page: any = {
    id: 0,
    pageNumber: 0,
    storyText: 'text',
    optionOnePageNumber: 0,
    optionOneText: 'text',
    optionTwoPageNumber: 0,
    optionTwoText: 'text',
    optionThreePageNumber: 0,
    optionThreeText: 'text',
    earlyStoryEnd: false,
    finalStoryEnd: false
  }

  firstTry: boolean = true;
  tryCounter = 0;

  constructor(private http: HttpClient) { }

  async getFirstPage(pageNumber) {
    let response = await fetch(`${environment.fothuZoneEC2Link}/cyoapages/page/${pageNumber}`);
    let responseObject = await response.json();
    this.page = responseObject;
    document.body.scrollTop = 0; // For Safari
    document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
  }
  async getPage(pageNumber) {
    let response = await fetch(`${environment.fothuZoneEC2Link}/cyoapages/page/${pageNumber}`);
    let responseObject = await response.json();
    this.page = responseObject;
    this.firstTry = false;
    document.body.scrollTop = 0; // For Safari
    document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
  }

  firstTryCheck() {
    return this.firstTry;
  }

  ngOnInit() {
    this.getFirstPage(1);
  }
}
