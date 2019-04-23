import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SegroupService } from '../segroup.service';
import { SEGroupBuilder } from '@SEGroup/segroup-model';
import SEClassFolder from 'libs/segroup-model/src/lib/SEClassFolder';
import SEClass from 'libs/segroup-model/src/lib/SEClass';
import Assignment from 'libs/segroup-model/src/lib/Assignment';
import Solution from 'libs/segroup-model/src/lib/Solution';

@Component({
  selector: 'SEGroup-rg-solutions',
  templateUrl: './rg-solutions.component.html',
  styleUrls: ['./rg-solutions.component.css']
})
export class RgSolutionsComponent implements OnInit {
  public gb: SEGroupBuilder;
  public currentTerm: SEClassFolder;
  public currentClass: SEClass;
  public currentAssignment: Assignment;
  public solutions: Solution[];
  
  constructor(
    private seGroupservice: SegroupService,
    private router: Router
  ) { 
    console.log("constructor RgSolutionsComponent");
    this.gb = this.seGroupservice.gb;
    this.currentTerm = this.seGroupservice.gb.getSeGroup().currentTerm;
    if (! this.currentTerm) {
      this.router.navigate(['/start']);  
    }
    this.currentClass = this.seGroupservice.gb.getSeGroup().currentClass;
    if ( ! this.currentClass) {
      this.router.navigate(['/se-classes']);
    }
    this.currentAssignment = this.seGroupservice.gb.getSeGroup().currentAssignment;
    if ( ! this.currentAssignment) {
      this.router.navigate(['/assignments']);
    }
    this.solutions = this.currentAssignment.solutions;
    this.updateSolutionsList();
  }


  ngOnInit() {
  }


  updateSolutionsList() {
    // loop through all students of our class and ensure that each student has a solution for this assignment
    for (const achievement of this.currentClass.participations) {
      this.gb.buildSolution(this.currentAssignment, achievement);
    }
  }


  gotoSolution(solution: Solution) {
    this.gb.getSeGroup().currentSolution = solution;
    this.router.navigate(['/solution-edit']);
  }

}
