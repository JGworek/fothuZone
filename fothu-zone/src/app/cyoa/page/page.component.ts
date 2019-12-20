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
    storyText: 'hi',
    optionOnePageNumber:0,
    optionOneText:'',
    optionTwoPageNumber:0,
    optionTwoText:'',
    optionThreePageNumber:0,
    optionThreeText:'',
    earlyStoryEnd:false,
    finalStoryEnd:false
  }

  constructor(private http: HttpClient) { }

  getPage(pageNumber) {
    let observable = this.http.get(`http://ec2-54-145-162-20.compute-1.amazonaws.com:3333/cyoapages/page/${pageNumber}`);
    observable.subscribe((result) => {
      this.page = result;
      console.log(this.page);
    },(error) => {
      console.log(error)
    })
    document.body.scrollTop = 0; // For Safari
    document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
    }

  ngOnInit() {

    let observable = this.http.get('http://ec2-54-145-162-20.compute-1.amazonaws.com:3333/cyoapages/page/1');
  observable.subscribe((result) => {
    this.page = result;
    console.log(this.page);
  },(error) => {
    console.log(error)
  })
  }

}
