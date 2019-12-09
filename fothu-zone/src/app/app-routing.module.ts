import { NgModule } from '@angular/core';
import { Routes, RouterModule} from '@angular/router';
import { LinksComponent } from './directory/links/links.component';

const routes: Routes = [
  { path: 'directory', component: LinksComponent}
]

@NgModule({
  declarations: [],
  imports: [ RouterModule.forRoot(routes) ], exports: [RouterModule]
})
export class AppRoutingModule { }