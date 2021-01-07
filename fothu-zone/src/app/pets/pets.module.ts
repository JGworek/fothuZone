import { NgModule } from '@angular/core';
import { NewPetComponent } from './new-pet/new-pet.component'
import { PetsHomeComponent } from './pets-home/pets-home.component';
import { AppRoutingModule } from '../app-routing.module';
import { CommonModule } from '@angular/common';
import { PetsComponent } from './pets/pets.component';
import { BattleComponent } from './battle/battle.component';
import { PetbarComponent } from './petbar/petbar.component';
import { MapComponent } from './map/map.component';
import { LevelUpComponent } from './level-up/level-up.component';



@NgModule({
  declarations: [
    NewPetComponent,
    PetsHomeComponent,
    PetsComponent,
    BattleComponent,
    PetbarComponent,
    MapComponent,
    LevelUpComponent,
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
  ],
  exports: [
    NewPetComponent,
    PetsHomeComponent,
    PetsComponent,
  ]
})
export class PetsModule { }
