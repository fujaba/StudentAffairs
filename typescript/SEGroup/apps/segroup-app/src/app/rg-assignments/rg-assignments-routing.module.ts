import { NgModule } from '@angular/core';
import { RgAssignmentsComponent } from './rg-assignments.component';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  { path: '', component: RgAssignmentsComponent },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RgAssignmentsRoutingModule { }
