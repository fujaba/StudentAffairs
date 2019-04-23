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
  { path: 'students', loadChildren: './rg-students/rg-students.module#RgStudentsModule'},
  { path: 'student-edit', loadChildren: './rg-student-edit/rg-student-edit.module#RgStudentEditModule'},
  { path: 'solutions', loadChildren: './rg-solutions/rg-solutions.module#RgSolutionsModule'},
  { path: 'solution-edit', loadChildren: './rg-solution-edit/rg-solution-edit.module#RgSolutionEditModule'},
  
]


@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(routes),
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
