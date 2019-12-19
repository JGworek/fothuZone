import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ScholaComponent } from './schola/schola.component';
import { PageComponent } from './page/page.component';
import { OptionsComponent } from './options/options.component';



@NgModule({
  declarations: [ScholaComponent, PageComponent, OptionsComponent],
  imports: [
    CommonModule
  ]
})
export class CyoaModule { }
