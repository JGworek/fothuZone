import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { HttpClientModule } from "@angular/common/http";

import { AppComponent } from "./app.component";
import { NavbarComponent } from "./navbar/navbar.component";
import { AppRoutingModule } from "./app-routing.module";
import { HomeComponent } from "./home/home.component";
import { PlaygroundPageComponent } from "./playground-page/playground-page.component";
import { FormsModule } from "@angular/forms";
import { NgbModal, NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { ProfileComponent } from "./profile/profile.component";
import { LoginComponent } from "./login/login.component";
import { CommonModule } from "@angular/common";
import { UserService } from "./service/user.service";
import { PetsModule } from "./pets/pets.module";
import { RosaryModule } from "./rosary/rosary.module";
import { CYOAModule } from "./cyoa/cyoa.module";
import { RouterModule } from "@angular/router";
import { SignUpComponent } from "./sign-up/sign-up.component";
import { BattleService } from "./service/battle.service";
import { LevelUpService } from "./service/level-up.service";
import { InjectableRxStompConfig, RxStompService, rxStompServiceFactory } from "@stomp/ng2-stompjs";
import { rxStompConfig } from "./rx-stomp.config";
import { ToastContainerComponent } from "./toast-container/toast-container.component";

@NgModule({
	declarations: [AppComponent, NavbarComponent, HomeComponent, PlaygroundPageComponent, ProfileComponent, LoginComponent, SignUpComponent, ToastContainerComponent],
	imports: [BrowserModule, AppRoutingModule, HttpClientModule, FormsModule, NgbModule, CommonModule, PetsModule, RosaryModule, RouterModule, CYOAModule, NgbModule],
	providers: [
		UserService,
		BattleService,
		LevelUpService,
		{ provide: InjectableRxStompConfig, useValue: rxStompConfig },
		{
			provide: RxStompService,
			useFactory: rxStompServiceFactory,
			deps: [InjectableRxStompConfig],
		},
	],
	bootstrap: [AppComponent],
})
export class AppModule {}
