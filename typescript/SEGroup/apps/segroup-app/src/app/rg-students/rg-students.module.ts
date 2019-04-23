import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RgStudentsComponent } from './rg-students.component';
import { RgStudentsRoutingModule } from './rg-students-routing.module';

@NgModule({
  declarations: [RgStudentsComponent],
  imports: [
    CommonModule,
    RgStudentsRoutingModule,
  ]
})
export class RgStudentsModule { }
