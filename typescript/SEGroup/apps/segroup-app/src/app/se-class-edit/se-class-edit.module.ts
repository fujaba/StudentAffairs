import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SeClassEditComponent } from './se-class-edit.component';
import { SegroupService } from '../segroup.service';
import { SeClassEditRoutingModule } from './se-class-edit-routing.module';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [SeClassEditComponent],
  imports: [
    CommonModule, 
    SeClassEditRoutingModule, 
    FormsModule
  ],
  providers: [
     
  ], 
})
export class SeClassEditModule { }
