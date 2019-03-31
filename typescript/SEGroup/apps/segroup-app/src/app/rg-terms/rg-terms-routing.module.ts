import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RgTermsComponent } from './rg-terms.component';

const routes: Routes = [
  { path: '', component: RgTermsComponent },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RGTermsRoutingModule { }
