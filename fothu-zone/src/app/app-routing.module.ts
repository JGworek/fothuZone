import { NgModule } from '@angular/core';
import { Routes, RouterModule} from '@angular/router';
import { LinksComponent } from './directory/links/links.component';
import { ScholaComponent } from './cyoa/schola/schola.component';

const routes: Routes = [
  { path: '', component:LinksComponent},
  { path: 'directory', component: LinksComponent},
  { path: 'schola', component: ScholaComponent}
]

@NgModule({
  declarations: [],
  imports: [ RouterModule.forRoot(routes) ], exports: [RouterModule]
})
export class AppRoutingModule { }