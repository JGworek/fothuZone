import { Component, OnInit } from '@angular/core';
import { ScholaService } from '../schola.service';

@Component({
  selector: 'app-page',
  templateUrl: './page.component.html',
  styleUrls: ['./page.component.css']
})
export class PageComponent implements OnInit {

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

  constructor(private scholaService: ScholaService) { }

  getPage(pageNumber) {
    this.page = this.scholaService.getPage(pageNumber);
  }

  ngOnInit() {

    this.page = this.scholaService.getFirstPage();
    console.log(this.page);
  }

}
