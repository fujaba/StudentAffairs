import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RgSolutionsComponent } from './rg-solutions.component';

const routes: Routes = [
  { path: '', component: RgSolutionsComponent },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RgSolutionsRoutingModule { }
