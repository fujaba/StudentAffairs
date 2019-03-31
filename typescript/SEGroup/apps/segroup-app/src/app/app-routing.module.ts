import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', loadChildren: './app.module#AppModule', pathMatch: 'full' },
  { path: 'start', loadChildren: './start/start.module#StartModule'},
  { path: 'terms', loadChildren: './rg-terms/rg-terms.module#RgTermsModule'},
  { path: 'se-classes', loadChildren: './se-classes/se-classes.module#SeClassesModule'},
  { path: 'se-class-edit', loadChildren: './se-class-edit/se-class-edit.module#SeClassEditModule'},
  { path: 'assignments', loadChildren: './rg-assignments/rg-assignments.module#RgAssignmentsModule'},
  { path: 'assignment-edit', loadChildren: './rg-assignment-edit/rg-assignment-edit.module#RgAssignmentEditModule'},
]


@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(routes),
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
