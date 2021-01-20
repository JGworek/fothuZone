import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { RosaryAppComponent } from "./rosary-app/rosary-app.component";
import { AppRoutingModule } from "../app-routing.module";
import { HymnalComponent } from './hymnal/hymnal.component';

@NgModule({
	declarations: [RosaryAppComponent, HymnalComponent],
	imports: [CommonModule, AppRoutingModule],
	exports: [RosaryAppComponent],
})
export class RosaryModule {}
