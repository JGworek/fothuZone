import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { AppRoutingModule } from './app-routing.module';
import { ScholaComponent } from './cyoa/schola/schola.component';
import { PageComponent } from './cyoa/page/page.component';
@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    ScholaComponent,
    PageComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
