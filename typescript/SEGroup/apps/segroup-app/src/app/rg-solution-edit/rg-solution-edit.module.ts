import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RgSolutionEditComponent } from './rg-solution-edit.component';
import { RgSolutionEditRoutingModule } from './rg-solution-edit-routing.module';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [RgSolutionEditComponent],
  imports: [
    CommonModule, 
    RgSolutionEditRoutingModule,
    FormsModule,
  ]
})
export class RgSolutionEditModule { }
