import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RgTermsComponent } from './rg-terms.component';
import { RGTermsRoutingModule } from './rg-terms-routing.module';

@NgModule({
  declarations: [RgTermsComponent],
  imports: [
    CommonModule, 
    RGTermsRoutingModule,
  ]
})
export class RgTermsModule { }
