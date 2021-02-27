import { Component, OnInit } from "@angular/core";
import { Image } from "src/app/models/Image";
import { PetDTO } from "src/app/models/PetDTO";
import { UserService } from "src/app/service/user.service";
import { environment } from "src/environments/environment";

@Component({
	selector: "app-new-pet",
	templateUrl: "./new-pet.component.html",
	styleUrls: ["./new-pet.component.css"],
})
export class NewPetComponent implements OnInit {
	constructor(private userService: UserService) {}

	numberOfPets: number = 16;

	availableNewPets: Array<Image> = [];
	newPetSelectionImage: Image;
	newPetImage: any;
	currentNewPet: PetDTO = {
		id: 0,
		name: "",
		imageId: 0,
		hunger: 0,
		type: "",
		agility: 0,
		strength: 0,
		intelligence: 0,
		petLevel: 1,
		curentXP: 0,
		currentHealth: 0,
		maxHealth: 0,
		ownerId: 0,
		availableLevelUps: 0,
	};
	imageSelected: boolean = false;
	unableToCreatePet: boolean = false;

	async getPetImages() {
		let petImagesJSON = await fetch(`${environment.fothuZoneEC2Link}/images/some?numberOfImages=${this.numberOfPets}`);
		this.availableNewPets = await petImagesJSON.json();
	}

	async createNewPetImage(imageString) {
		// this.newPetSelectionImage = { id: 0, imageURL: imageString, imageOwnerUsername: usernameString, imageOwnerName: nameString };
		// console.log(this.newPetSelectionImage);
		// let imageResponse = await fetch(`${environment.fothuZoneEC2Link}/pets/image/new`, { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(this.newPetSelectionImage) });
		// this.newPetImage = await imageResponse.json();
		// if (imageResponse.status.toString()[0] == '1' || imageResponse.status.toString()[0] == '4' || imageResponse.status.toString()[0] == '5') {
		//   this.unableToSelectImage = true;
		// } else {
		//   this.petImageSelected = true;
		// }
	}

	selectPetImage(imageId: number, imageURL: string) {
		this.newPetImage = imageURL;
		this.currentNewPet.imageId = imageId;
		this.currentNewPet.ownerId = this.userService.currentUser.id;
		this.imageSelected = true;
	}

	showNewPet() {
		console.log(this.currentNewPet);
	}

	checkIfFormCompleted() {
		if (this.currentNewPet.name == "") {
			return false;
		} else if (this.currentNewPet.type == "") {
			return false;
		} else {
			return true;
		}
	}

	ngOnInit(): void {
		this.getPetImages();
	}
}
