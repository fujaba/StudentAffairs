import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', loadChildren: './app.module#AppModule', pathMatch: 'full' },
  { path: 'start', loadChildren: './start/start.module#StartModule'},
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
