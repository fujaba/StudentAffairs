import { NgModule } from '@angular/core';
import { RgAssignmentEditComponent } from './rg-assignment-edit.component';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  { path: '', component: RgAssignmentEditComponent },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RgAssignmentEditRoutingModule { }
