import { NgModule } from "@angular/core";
import { NewPetComponent } from "./new-pet/new-pet.component";
import { PetsHomeComponent } from "./pets-home/pets-home.component";
import { AppRoutingModule } from "../app-routing.module";
import { CommonModule } from "@angular/common";
import { BattleComponent } from "./battle/battle.component";
import { PetbarComponent } from "./petbar/petbar.component";
import { DungeonComponent } from "./dungeon/dungeon.component";
import { LevelUpComponent } from "./level-up/level-up.component";
import { CreateChallengeComponent } from "./create-challenge/create-challenge.component";
import { FormsModule } from "@angular/forms";
import { FilterPipeModule } from "ngx-filter-pipe";
import { DungeonCreatorComponent } from "./dungeon-creator/dungeon-creator.component";

@NgModule({
	declarations: [NewPetComponent, PetsHomeComponent, BattleComponent, PetbarComponent, DungeonComponent, LevelUpComponent, CreateChallengeComponent, DungeonCreatorComponent],
	imports: [CommonModule, AppRoutingModule, FormsModule, FilterPipeModule],
	exports: [],
})
export class PetsModule {}
