import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { AppRoutingModule } from './app-routing.module';
import { PageComponent } from './cyoa/page/page.component';
import { LinksComponent } from './directory/links/links.component';
import { PetSelectionComponent } from './pet-selection/pet-selection.component';
import { RosaryAppComponent } from './rosary/rosary-app/rosary-app.component';
import { PlaygroundPageComponent } from './playground-page/playground-page.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    PageComponent,
    LinksComponent,
    PetSelectionComponent,
    RosaryAppComponent,
    PlaygroundPageComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
