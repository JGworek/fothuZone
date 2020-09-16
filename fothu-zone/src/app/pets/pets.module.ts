import { NgModule } from '@angular/core';
import { NewPetComponent } from './new-pet/new-pet.component'
import { PetsHomeComponent } from './pets-home/pets-home.component';
import { PveComponent } from './pve/pve.component';
import { PvpComponent } from './pvp/pvp.component';
import { BattleResultComponent } from './battle-result/battle-result.component';
import { AppRoutingModule } from '../app-routing.module';
import { CommonModule } from '@angular/common';
import { PetsComponent } from './pets/pets.component';
import { NewPetDetailsComponent } from './new-pet-details/new-pet-details.component';
import { BattleComponent } from './battle/battle.component';
import { PetbarComponent } from './petbar/petbar.component';
import { MapComponent } from './map/map.component';



@NgModule({
  declarations: [
    NewPetComponent,
    PetsHomeComponent,
    PveComponent,
    PvpComponent,
    BattleResultComponent,
    PetsComponent,
    NewPetDetailsComponent,
    BattleComponent,
    PetbarComponent,
    MapComponent,
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
  ],
  exports: [
    NewPetComponent,
    PetsHomeComponent,
    PveComponent,
    PvpComponent,
    BattleResultComponent,
    PetsComponent,
  ]
})
export class PetsModule { }
