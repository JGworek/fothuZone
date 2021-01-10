import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { HttpClientModule } from "@angular/common/http";

import { AppComponent } from "./app.component";
import { NavbarComponent } from "./navbar/navbar.component";
import { AppRoutingModule } from "./app-routing.module";
import { HomeComponent } from "./home/home.component";
import { PlaygroundPageComponent } from "./playground-page/playground-page.component";
import { FormsModule } from "@angular/forms";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { ProfileComponent } from "./profile/profile.component";
import { LoginComponent } from "./login/login.component";
import { CommonModule } from "@angular/common";
import { UserService } from "./service/user.service";
import { PetsModule } from "./pets/pets.module";
import { RosaryModule } from "./rosary/rosary.module";
import { CYOAModule } from "./cyoa/cyoa.module";
import { FilterPipeModule } from "ngx-filter-pipe";

import { RouterModule } from "@angular/router";

@NgModule({
	declarations: [AppComponent, NavbarComponent, HomeComponent, PlaygroundPageComponent, ProfileComponent, LoginComponent],
	imports: [BrowserModule, AppRoutingModule, HttpClientModule, FormsModule, NgbModule, CommonModule, PetsModule, RosaryModule, RouterModule, CYOAModule, FilterPipeModule],
	providers: [UserService],
	bootstrap: [AppComponent],
})
export class AppModule {}
