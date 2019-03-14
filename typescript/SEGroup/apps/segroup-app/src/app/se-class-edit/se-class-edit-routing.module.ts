import { NgModule } from '@angular/core';
import { SeClassEditComponent } from './se-class-edit.component';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  { path: '', component: SeClassEditComponent },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SeClassEditRoutingModule { }
