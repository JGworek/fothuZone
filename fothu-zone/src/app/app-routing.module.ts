import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LinksComponent } from './directory/links/links.component';
import { PageComponent } from './cyoa/page/page.component';
import { RosaryAppComponent } from './rosary/rosary-app/rosary-app.component';
import { PlaygroundPageComponent } from './playground-page/playground-page.component';
import { LoginComponent } from './login/login.component';
import { ProfileComponent } from './profile/profile.component';
import { PetsHomeComponent } from './pets/pets-home/pets-home.component';
import { NewPetComponent } from './pets/new-pet/new-pet.component';
import { PveComponent } from './pets/pve/pve.component';
import { PvpComponent } from './pets/pvp/pvp.component';
import { BattleResultComponent } from './pets/battle-result/battle-result.component';
import { NewPetDetailsComponent } from './pets/new-pet-details/new-pet-details.component';
import { BattleComponent } from './pets/battle/battle.component';
import { PetbarComponent } from './pets/petbar/petbar.component';
import { MapComponent } from './pets/map/map.component';
import { UserGuardGuard as UserGuard } from './auth/user-guard.guard';

const routes: Routes = [
  { path: 'directory', component: LinksComponent },
  { path: 'schola', component: PageComponent },
  { path: 'directory/schola', component: PageComponent },
  { path: 'rosary', component: RosaryAppComponent },
  { path: 'directory/rosary', component: RosaryAppComponent },
  { path: 'playground', component: PlaygroundPageComponent },
  { path: 'login', component: LoginComponent },
  { path: 'profile', component: ProfileComponent, canActivate: [UserGuard] },
  {
    path: 'FothuPets', children: [
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      { path: 'home', component: PetsHomeComponent },
      { path: 'new', component: NewPetComponent },
      { path: 'new/details', component: NewPetDetailsComponent },
      { path: 'pve', component: PveComponent },
      { path: 'pvp', component: PvpComponent },
      { path: 'battle', component: BattleComponent },
      { path: 'battle/battleresult', component: BattleResultComponent },
      { path: 'map', component: MapComponent },
    ]
  },
  { path: 'visible', component: PetbarComponent, outlet: "petbar" },
  { path: 'battle', component: BattleComponent, outlet: "battle"},
  { path: '', redirectTo: 'directory', pathMatch: 'full' },
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