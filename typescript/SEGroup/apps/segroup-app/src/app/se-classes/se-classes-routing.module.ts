import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SeClassesComponent } from './se-classes.component';

const routes: Routes = [
  { path: '', component: SeClassesComponent },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SeClassesRoutingModule { }
