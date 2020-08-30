import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms'
import { ProfileService } from '../service/profile.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-playground-page',
  templateUrl: './playground-page.component.html',
  styleUrls: ['./playground-page.component.css']
})
export class PlaygroundPageComponent implements OnInit {

  constructor(public profileService: ProfileService, private router: Router) { }

  formObject: any;
  title: any = "wut";
  synopsis: any = "wut";
  

  //ALL OF THIS IS THE FIRST BUTTON
  laterMessage = function() {
    console.log("And this happens later");
  }

  clickButton() {
    console.log("Hey you clicked the button");
    this.thingThatHappensLater(this.laterMessage);
  }

  thingThatHappensLater(message) {
    setTimeout(message, 3000);
  }
//----------------------------------------------------------------------

//ALL OF THIS IS THE SECOND BUTTON
  clickButtonTwo() {
    setTimeout(()=>{console.log("Boom Shakalaka")},5000);
  }
//----------------------------------------------------------------------

onSubmit(f: NgForm) {
    console.log(f.value);  
    console.log(f.value.title);
    console.log(f.value.synopsis);
    console.log(typeof(f.value.title));
this.title = f.value.title;
this.synopsis = f.value.synopsis;
}

resolveAfter2Seconds() {
  return new Promise(resolve => {
    setTimeout(() => {
      resolve('resolved');
    }, 2000);
  });
}

async doAFormyThing() {
  console.log("calling");
  const result = await this.resolveAfter2Seconds();
  console.log(result);

}

hello() {
  console.log(typeof(this.hi()));
  let x = this.hi();
  //beacuse of ngZone, this promise is usable and prints
  console.log(x);
  this.hi().then((promiseContents)=>{console.log(promiseContents)})};


  //because hi is async, it actually returns whatever it returns inside a Promise
async hi() {
  return("hi you nerds");
}

otherAsyncThing() {
  var d = new Date();
  this.callAFunction(this.one, this.two, d)
    .then((result)=>{
    console.log(result);
  })
  .then(()=>{
    setTimeout(this.one, 0)
  })
}

one = function() {
  var d = new Date();
  console.log(d.getTime());
}

two = function() {
  console.log("2");
}

async callAFunction(first, second, d) {
  console.log(d.getTime());
  const x = await Promise.resolve("Hello");
  // console.log(d.getTime());
  return x;
}

resolveAfter4Seconds(x) { 
  return new Promise(resolve => {
    setTimeout(() => {
      resolve(x);
    }, 4000);
  });
}

async f1() {
  var x = await this.resolveAfter4Seconds(10);
  console.log(x); // 10
}

  ngOnInit() {
    if(this.profileService.currentUser.username.toLowerCase() != 'fothu') {
      this.router.navigate(['/directory']);
    }
  }

}
