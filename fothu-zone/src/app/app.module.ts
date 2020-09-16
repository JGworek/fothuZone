import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { AppRoutingModule } from './app-routing.module';
import { PageComponent } from './cyoa/page/page.component';
import { LinksComponent } from './directory/links/links.component';
import { PlaygroundPageComponent } from './playground-page/playground-page.component';
import { FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ProfileComponent } from './profile/profile.component';
import { LoginComponent } from './login/login.component';
import { CommonModule } from '@angular/common';
import { ProfileService } from './service/profile.service';
import { PetsModule } from './pets/pets.module';
import { RosaryModule } from './rosary/rosary.module';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    PageComponent,
    LinksComponent,
    PlaygroundPageComponent,
    ProfileComponent,
    LoginComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NgbModule,
    CommonModule,
    PetsModule,
    RosaryModule,
  ],
  providers: [
    ProfileService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
