import { NgModule } from '@angular/core';
import { RgStudentEditComponent } from './rg-student-edit.component';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  { path: '', component: RgStudentEditComponent },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RgStudentEditRoutingModule { }
