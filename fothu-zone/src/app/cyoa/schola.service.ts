import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ScholaService {

  page: any = {
    id: 0,
    pageNumber: 0,
    storyText: '',
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

  getFirstPage() {
  let observable = this.http.get('http://ec2-54-145-162-20.compute-1.amazonaws.com:3333/cyoapages/page/1');
  observable.subscribe((result) => {
    this.page = result;
    console.log(this.page);
    return this.page;
  },(error) => {
    console.log(error)
  })
}

  getPage(pageNumber: number) {
  let observable = this.http.get(`ec2-54-145-162-20.compute-1.amazonaws.com:3333/cyoapages/page/${pageNumber}`);
  observable.subscribe((result) => {
    this.page = result;
    return this.page;
  },(error) => {
    console.log(error)
  })
}

}