import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SegroupService } from '../segroup.service';
import { SEGroupBuilder } from '@SEGroup/segroup-model';
import SEClassFolder from 'libs/segroup-model/src/lib/SEClassFolder';
import Assignment from 'libs/segroup-model/src/lib/Assignment';
import SEClass from 'libs/segroup-model/src/lib/SEClass';

@Component({
  selector: 'SEGroup-rg-assignments',
  templateUrl: './rg-assignments.component.html',
  styleUrls: ['./rg-assignments.component.css']
})
export class RgAssignmentsComponent implements OnInit {
  public gb: SEGroupBuilder;
  public currentTerm: SEClassFolder;
  public currentClass: SEClass;
  public assignments: Assignment[];
  
  constructor(
    private seGroupservice: SegroupService,
    private router: Router
  ) { 
    console.log("constructor RgAssignmentsComponent");
    this.gb = this.seGroupservice.gb;
    this.currentTerm = this.seGroupservice.gb.getSeGroup().currentTerm;
    if (! this.currentTerm) {
      this.router.navigate(['/start']);  
    }
    this.currentClass = this.seGroupservice.gb.getSeGroup().currentClass;
    if ( ! this.currentClass) {
      this.router.navigate(['/se-classes']);
    }
    this.assignments = this.currentClass.assignments;
    if (this.assignments.length == 0) {
      this.router.navigate(['/assignment-edit']);  
    }
  }

  ngOnInit() {
  }

}
