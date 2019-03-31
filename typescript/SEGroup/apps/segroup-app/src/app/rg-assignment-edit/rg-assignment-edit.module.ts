import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RgAssignmentEditComponent } from './rg-assignment-edit.component';
import { RgAssignmentEditRoutingModule } from './rg-assignment-edit-routing.module';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [RgAssignmentEditComponent],
  imports: [
    CommonModule, 
    RgAssignmentEditRoutingModule,
    FormsModule,
  ]
})
export class RgAssignmentEditModule { }
