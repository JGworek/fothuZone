import { NgModule } from '@angular/core';
import { NewPetComponent } from './new-pet/new-pet.component'
import { PetsHomeComponent } from './pets-home/pets-home.component';
import { PveComponent } from './pve/pve.component';
import { PvpComponent } from './pvp/pvp.component';
import { BattleResultComponent } from './battle-result/battle-result.component';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from '../app-routing.module';
import { FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { PetsComponent } from './pets/pets.component';
import { RouterModule } from '@angular/router';
import { NewPetDetailsComponent } from './new-pet-details/new-pet-details.component';
import { BattleComponent } from './battle/battle.component';



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
