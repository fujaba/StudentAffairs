import { Component, OnInit } from '@angular/core';
import { SEGroupBuilder } from '@SEGroup/segroup-model';
import SEClassFolder from 'libs/segroup-model/src/lib/SEClassFolder';
import { Router } from '@angular/router';
import { SegroupService } from '../segroup.service';
import SEClass from 'libs/segroup-model/src/lib/SEClass';
import Assignment from 'libs/segroup-model/src/lib/Assignment';

@Component({
  selector: 'SEGroup-rg-assignment-edit',
  templateUrl: './rg-assignment-edit.component.html',
  styleUrls: ['./rg-assignment-edit.component.css']
})
export class RgAssignmentEditComponent implements OnInit {
  public gb: SEGroupBuilder;
  public currentTerm: SEClassFolder;
  public currentClass: SEClass;
  public currentAssignment: Assignment;
  public task: string;
  
  constructor(
    private seGroupservice: SegroupService,
    private router: Router
  ) { 
    console.log("constructor RgAssignmentEditComponent");
    this.gb = this.seGroupservice.gb;
    this.currentTerm = this.seGroupservice.gb.getSeGroup().currentTerm;
    this.currentClass = this.seGroupservice.gb.getSeGroup().currentClass;
    this.currentAssignment = this.seGroupservice.gb.getSeGroup().currentAssignment;
    if (this.currentAssignment) {
      this.task = this.currentAssignment.task;
    }

    if (! this.currentTerm) {
      this.router.navigate(['/start']);  
    }
    
  }

  ngOnInit() {
  }

  public okAction() {
    console.log('OK Action for ' + this.task); 
    if ( ! this.currentAssignment) {
      console.log(` got ${this.task}` );
      this.gb.buildAssignment(this.task, this.currentClass);
    }
    
    this.router.navigate(['/assignments']);
  }

  public cancelAction() {
    this.router.navigate(['/assignments']);
  }

}
