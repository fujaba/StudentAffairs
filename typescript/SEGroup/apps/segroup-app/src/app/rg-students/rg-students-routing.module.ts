import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RgStudentsComponent } from './rg-students.component';

const routes: Routes = [
  { path: '', component: RgStudentsComponent },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RgStudentsRoutingModule { }
