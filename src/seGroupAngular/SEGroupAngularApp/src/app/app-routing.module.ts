import { NgModule } from '@angular/core';
import { RouterModule, Routes } from "@angular/router";

import { SEGroupTermsComponent } from "./segroup-terms/segroup-terms.component";
import { DashboardComponent }   from './dashboard/dashboard.component';
import { SEClassDetailsComponent }  from './seclass-details/seclass-details.component';

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'seclasses', component: SEGroupTermsComponent},
  { path: 'detail/:id', component: SEClassDetailsComponent}
]


@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [RouterModule]
})

export class AppRoutingModule { }
