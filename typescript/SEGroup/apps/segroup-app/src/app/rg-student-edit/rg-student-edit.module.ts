import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RgStudentEditComponent } from './rg-student-edit.component';
import { RgStudentEditRoutingModule } from './rg-student-edit-routing.module';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [RgStudentEditComponent],
  imports: [
    CommonModule, 
    RgStudentEditRoutingModule, 
    FormsModule,
  ]
})
export class RgStudentEditModule { }
