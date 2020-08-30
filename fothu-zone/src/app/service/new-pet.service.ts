import { Injectable } from '@angular/core';
import { PetDTO } from '../models/PetDTO';
import { Image } from '../models/Image';

@Injectable({
  providedIn: 'root'
})
export class NewPetService {

  currentNewPet: PetDTO = {
    id: 0,
    name: "",
    imageId: 0,
    hunger: 0,
    type: "",
    agility: 0,
    strength: 0,
    intelligence: 0,
    level: 1,
    curentXP: 0,
    currentHealth: 0,
    maxHealth: 0,
    ownerId: 0,
  }

  currentNewImage: Image = {
    id: 0,
    imageURL: "",
    imageOwnerUsername: "",
    imageOwnerName: "",
  }

  constructor() { }
}
