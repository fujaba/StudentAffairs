import { Component, OnInit } from '@angular/core';
import { SEGroupBuilder } from '@SEGroup/segroup-model';
import SEClassFolder from 'libs/segroup-model/src/lib/SEClassFolder';
import SEClass from 'libs/segroup-model/src/lib/SEClass';
import Assignment from 'libs/segroup-model/src/lib/Assignment';
import Solution from 'libs/segroup-model/src/lib/Solution';
import { Router } from '@angular/router';
import { SegroupService } from '../segroup.service';

@Component({
  selector: 'SEGroup-rg-solution-edit',
  templateUrl: './rg-solution-edit.component.html',
  styleUrls: ['./rg-solution-edit.component.css']
})
export class RgSolutionEditComponent implements OnInit {
  public gb: SEGroupBuilder;
  public currentTerm: SEClassFolder;
  public currentClass: SEClass;
  public currentAssignment: Assignment;
  public currentSolution: Solution

  public points: string;
  
  constructor(
    private seGroupservice: SegroupService,
    private router: Router
    ) { 
    console.log("constructor RgSolutionEditComponent");
    this.gb = this.seGroupservice.gb;
    this.currentTerm = this.seGroupservice.gb.getSeGroup().currentTerm;
    this.currentClass = this.seGroupservice.gb.getSeGroup().currentClass;
    this.currentAssignment = this.seGroupservice.gb.getSeGroup().currentAssignment;
    this.currentSolution = this.seGroupservice.gb.getSeGroup().currentSolution;
  }


  ngOnInit() {
  }

  okAction() {
    console.log(Number(this.points));
    this.gb.gradeSolution(this.currentSolution, Number(this.points));
  }

}
