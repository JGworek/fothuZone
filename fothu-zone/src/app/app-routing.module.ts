import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { HomeComponent } from "./home/home.component";
import { PageComponent } from "./cyoa/page/page.component";
import { RosaryAppComponent } from "./rosary/rosary-app/rosary-app.component";
import { PlaygroundPageComponent } from "./playground-page/playground-page.component";
import { LoginComponent } from "./login/login.component";
import { ProfileComponent } from "./profile/profile.component";
import { PetsHomeComponent } from "./pets/pets-home/pets-home.component";
import { NewPetComponent } from "./pets/new-pet/new-pet.component";
import { BattleComponent } from "./pets/battle/battle.component";
import { PetbarComponent } from "./pets/petbar/petbar.component";
import { DungeonComponent } from "./pets/dungeon/dungeon.component";
import { UserGuard } from "./guard/user.guard";
import { LevelUpComponent } from "./pets/level-up/level-up.component";
import { CreateChallengeComponent } from "./pets/create-challenge/create-challenge.component";
import { HymnalComponent } from "./rosary/hymnal/hymnal.component";
import { PreventExitGuard } from "./guard/prevent-exit.guard";
import { SignUpComponent } from "./sign-up/sign-up.component";
import { CommonModule } from "@angular/common";

const routes: Routes = [
	{ path: "", redirectTo: "home", pathMatch: "full" },
	{ path: "home", component: HomeComponent },
	{ path: "schola", component: PageComponent },
	{ path: "rosary", component: RosaryAppComponent },
	{
		path: "hymnal",
		component: HymnalComponent,
		// resolve: { url: "https://fothuzone-rosary.s3.amazonaws.com/cp3u.pdf" }
	},
	{ path: "playground", component: PlaygroundPageComponent },
	{ path: "login", component: LoginComponent },
	{ path: "profile", component: ProfileComponent, canActivate: [UserGuard] },
	{ path: "FothuPets", component: PetsHomeComponent, canActivate: [UserGuard] },
	{ path: "newPet", component: NewPetComponent, canActivate: [UserGuard] },
	{ path: "challenge", component: CreateChallengeComponent, canActivate: [UserGuard] },
	{ path: "dungeon", component: DungeonComponent, canActivate: [UserGuard], canDeactivate: [PreventExitGuard] },
	{ path: "available", component: PetbarComponent, outlet: "petbar", canActivate: [UserGuard] },
	{ path: "available", component: BattleComponent, outlet: "battles", canActivate: [UserGuard] },
	{ path: "possible", component: LevelUpComponent, outlet: "levelUps", canActivate: [UserGuard] },
	{ path: "signUp", component: SignUpComponent, canActivate: [!UserGuard] },
	// { path: '**', redirectTo: 'directory', pathMatch: 'full' },
];

@NgModule({
	declarations: [],
	imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: "reload" }), CommonModule],
	exports: [RouterModule],
})
export class AppRoutingModule {}

// children: [
//   {path: 'home', component:PetsHomeComponent, outlet: 'pets'},
//   {path: 'new', component:NewPetComponent, outlet: 'pets'},
// ]},
