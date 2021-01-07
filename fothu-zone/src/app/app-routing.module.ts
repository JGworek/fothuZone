import { NgModule } from '@angular/core';
import { Routes, RouterModule, PreloadAllModules } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { PageComponent } from './cyoa/page/page.component';
import { RosaryAppComponent } from './rosary/rosary-app/rosary-app.component';
import { PlaygroundPageComponent } from './playground-page/playground-page.component';
import { LoginComponent } from './login/login.component';
import { ProfileComponent } from './profile/profile.component';
import { PetsHomeComponent } from './pets/pets-home/pets-home.component';
import { NewPetComponent } from './pets/new-pet/new-pet.component';
import { BattleComponent } from './pets/battle/battle.component';
import { PetbarComponent } from './pets/petbar/petbar.component';
import { MapComponent } from './pets/map/map.component';
import { UserGuardGuard as UserGuard } from './auth/user-guard.guard';
import { LevelUpComponent } from './pets/level-up/level-up.component';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full'},
  { path: 'home', component: HomeComponent },
  { path: 'schola', component: PageComponent },
  { path: 'rosary', component: RosaryAppComponent },
  { path: 'playground', component: PlaygroundPageComponent },
  { path: 'login', component: LoginComponent },
  { path: 'profile', component: ProfileComponent, canActivate: [UserGuard] },
  { path: 'FothuPets', component: PetsHomeComponent },
  { path: 'newPet', component: NewPetComponent },
  { path: 'dungeon', component: MapComponent },
  { path: 'available', component: PetbarComponent, outlet: "petbar" },
  { path: 'available', component: BattleComponent, outlet: "battles"},
  { path: 'possible', component: LevelUpComponent, outlet: "levelUps"},
  // { path: '**', redirectTo: 'directory', pathMatch: 'full' },
]

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' }),
  ], exports: [RouterModule]
})
export class AppRoutingModule { }


// children: [
    //   {path: 'home', component:PetsHomeComponent, outlet: 'pets'},
    //   {path: 'new', component:NewPetComponent, outlet: 'pets'},
    // ]},