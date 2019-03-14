import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', redirectTo: '/se-classes', pathMatch: 'full' },
  { path: 'se-classes', loadChildren: './se-classes/se-classes.module#SeClassesModule'},
  { path: 'se-class-edit', loadChildren: './se-class-edit/se-class-edit.module#SeClassEditModule'}
]


@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(routes),
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
