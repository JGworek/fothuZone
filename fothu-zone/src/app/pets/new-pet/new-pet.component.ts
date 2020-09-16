import { Component, OnInit } from '@angular/core';
import { Image } from 'src/app/models/Image';
import { environment } from 'src/environments/environment';




@Component({
  selector: 'app-new-pet',
  templateUrl: './new-pet.component.html',
  styleUrls: ['./new-pet.component.css']
})
export class NewPetComponent implements OnInit {

  constructor() { }

  numberOfPets: number = 20;

  availableNewPets: any;
  newPetSelectionImage: Image;
  newPetImage: any;
  petImageSelected: boolean = false;
  unableToSelectImage: boolean = false;

  async getPets() {
    let petResponse = await fetch(`https://api.unsplash.com/photos/random?query=animal&orientation=squarish&count=${this.numberOfPets}&client_id=MKjZ1XUDw1fFxzcMsjntxmHOMh8b4QKM58bKDyuG19Q`);
    console.log(petResponse);
    this.availableNewPets = await petResponse.json();
    console.log(this.availableNewPets);
  }

  async createNewPetImage(imageString, usernameString, nameString) {
    this.newPetSelectionImage = { id: 0, imageURL: imageString, imageOwnerUsername: usernameString, imageOwnerName: nameString };
    console.log(this.newPetSelectionImage);
    // let imageResponse = await fetch(`http://localhost:6969/pets/image/new`, {method: 'POST', headers: {'Content-Type': 'application/json'}, body: JSON.stringify(this.newPetSelectionImage)});
    let imageResponse = await fetch(`${environment.fothuZoneEC2Link}/pets/image/new`, { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(this.newPetSelectionImage) });
    this.newPetImage = await imageResponse.json();
    if (imageResponse.status.toString()[0] == '1' || imageResponse.status.toString()[0] == '4' || imageResponse.status.toString()[0] == '5') {
      this.unableToSelectImage = true;
    } else {
      this.petImageSelected = true;
    }
  }


  ngOnInit(): void {
    this.getPets();
  }

}
