import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RgAssignmentsComponent } from './rg-assignments.component';
import { RgAssignmentsRoutingModule } from '../rg-assignments/rg-assignments-routing.module';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [RgAssignmentsComponent],
  imports: [
    CommonModule, 
    RgAssignmentsRoutingModule,
    FormsModule,
  ]
})
export class RgAssignmentsModule { }
