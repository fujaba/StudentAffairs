import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RgSolutionsComponent } from './rg-solutions.component';
import { RgSolutionsRoutingModule } from './rg-solutions-routing.module';

@NgModule({
  declarations: [RgSolutionsComponent],
  imports: [
    CommonModule, 
    RgSolutionsRoutingModule,
  ]
})
export class RgSolutionsModule { }
