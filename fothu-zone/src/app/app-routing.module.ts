import { NgModule } from '@angular/core';
import { Routes, RouterModule} from '@angular/router';
import { LinksComponent } from './directory/links/links.component';
import { PetSelectionComponent } from './pet-selection/pet-selection.component';
import { PageComponent } from './cyoa/page/page.component';
import { RosaryAppComponent } from './rosary/rosary-app/rosary-app.component';

const routes: Routes = [
  { path: '', component:LinksComponent},
  { path: 'directory', component: LinksComponent},
  { path: 'schola', component: PageComponent},
  { path: 'petSelection', component: PetSelectionComponent},
  { path: 'rosary', component: RosaryAppComponent}
]

@NgModule({
  declarations: [],
  imports: [ RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'}) ], exports: [RouterModule]
})
export class AppRoutingModule { }