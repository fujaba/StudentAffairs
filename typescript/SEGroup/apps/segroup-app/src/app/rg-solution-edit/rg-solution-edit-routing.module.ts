import { NgModule } from '@angular/core';
import { RgSolutionEditComponent } from './rg-solution-edit.component';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  { path: '', component: RgSolutionEditComponent },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RgSolutionEditRoutingModule { }
