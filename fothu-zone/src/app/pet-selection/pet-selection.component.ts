import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-pet-selection',
  templateUrl: './pet-selection.component.html',
  styleUrls: ['./pet-selection.component.css']
})



export class PetSelectionComponent implements OnInit {

  constructor(private http: HttpClient) { }
  firstBirdImage: String = '';
  secondBirdImage: String = '';
  thirdBirdImage: String = '';
  currentBird: any;
  firstPandaImage: String = '';
  secondPandaImage: String = '';
  thirdPandaImage: String = '';
  currentPanda: any;


/*https://cors-anywhere.herokuapp.com/URL gets around CORS*/

  /*getRandomBirds() {
    let i: number = 1;
    while(i<=3) {
      console.log(i);
      const request = async () => {
      const response = await fetch('https://cors-anywhere.herokuapp.com/https://some-random-api.ml/img/birb')
      const birdJson = await response.json();
      request();
        this.currentBird = birdJson;
        console.log(this.currentBird.link);
        console.log(i);
        if(i == 1) {
          this.firstBirdImage = this.currentBird.link;
        }else if (i == 2) {
          this.secondBirdImage = this.currentBird.link;
          if(this.secondBirdImage == this.firstBirdImage) {
            console.log("inside");
            i--;
            console.log(i);
          }
        }else if (i == 3) {
          this.thirdBirdImage = this.currentBird.link
          if(this.thirdBirdImage == this.secondBirdImage || this.thirdBirdImage == this.firstBirdImage) {
            i--;
          }
        }i++;
      }
    }
    }*/

  // getRandomPandas() {
  //   for (let i = 1; i <= 3; i++) {
  //     let pandaservable = this.http.get('https://cors-anywhere.herokuapp.com/https://some-random-api.ml/img/panda');
  //     pandaservable.subscribe(pandasult => {
  //       this.currentPanda = pandasult;
  //       console.log(this.currentPanda.link);
  //       if (i == 1) {
  //         this.firstPandaImage = this.currentPanda.link;
  //       } else if (i == 2) {
  //         this.secondPandaImage = this.currentPanda.link;
  //       } else if (i == 3) {
  //         this.thirdPandaImage = this.currentPanda.link
  //       }
  //     })
  //   }
  // }

  ngOnInit() {
    //this.getRandomBirds();
    // this.getRandomPandas();
  }
}
