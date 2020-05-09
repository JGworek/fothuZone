import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

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
    optionOnePageNumber:0,
    optionOneText:'text',
    optionTwoPageNumber:0,
    optionTwoText:'text',
    optionThreePageNumber:0,
    optionThreeText:'text',
    earlyStoryEnd:false,
    finalStoryEnd:false
  }

  firstTry: boolean = true;

  constructor(private http: HttpClient) { }

  //test

  getPage(pageNumber) {

    let observable = this.http.get(`http://http://ec2-54-161-212-213.compute-1.amazonaws.com:6969/cyoapages/page/${pageNumber}`);
    observable.subscribe((result) => {
      this.page = result;
    },(error) => {
      console.log(error)
    })
    this.firstTry = false;
    document.body.scrollTop = 0; // For Safari
    document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
    }

  firstTryCheck() {
    return this.firstTry;
  }

  ngOnInit() {
    this.getPage(1);
    this.firstTry = true;
  }
}
