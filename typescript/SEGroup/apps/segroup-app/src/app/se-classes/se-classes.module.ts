import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SeClassesComponent } from './se-classes.component';
import { SeClassesRoutingModule } from './se-classes-routing.module';
import { SegroupService } from '../segroup.service';

@NgModule({
  declarations: [
    SeClassesComponent,
  ],
  imports: [
    CommonModule,
    SeClassesRoutingModule,
  ], 
  providers: [
     
  ], 
  entryComponents: [
  ]
})
export class SeClassesModule { }
