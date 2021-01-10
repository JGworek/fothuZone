import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { PageComponent } from "./page/page.component";
import { AppRoutingModule } from "../app-routing.module";

@NgModule({
	declarations: [PageComponent],
	imports: [CommonModule, AppRoutingModule],
	exports: [PageComponent],
})
export class CYOAModule {}
