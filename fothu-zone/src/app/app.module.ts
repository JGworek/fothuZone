import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { AppRoutingModule } from './app-routing.module';
import { LinksComponent } from './directory/links/links.component';
import { ScholaComponent } from './cyoa/schola/schola.component';
import { PageComponent } from './cyoa/page/page.component';
import { OptionsComponent } from './cyoa/options/options.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    LinksComponent,
    ScholaComponent,
    PageComponent,
    OptionsComponent
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
