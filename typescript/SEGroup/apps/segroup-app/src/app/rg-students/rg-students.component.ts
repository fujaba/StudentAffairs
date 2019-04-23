import { Component, OnInit } from '@angular/core';
import { SegroupService } from '../segroup.service';
import { Router } from '@angular/router';
import { SEGroupBuilder } from '@SEGroup/segroup-model';
import SEClassFolder from 'libs/segroup-model/src/lib/SEClassFolder';
import SEClass from 'libs/segroup-model/src/lib/SEClass';
import Achievement from 'libs/segroup-model/src/lib/Achievement';
import SEStudent from 'libs/segroup-model/src/lib/SEStudent';

@Component({
  selector: 'SEGroup-rg-students',
  templateUrl: './rg-students.component.html',
  styleUrls: ['./rg-students.component.css']
})
export class RgStudentsComponent implements OnInit {
  public gb: SEGroupBuilder;
  public currentTerm: SEClassFolder;
  public currentClass: SEClass;
  public achievements: Achievement[];
  
  constructor(
    private seGroupservice: SegroupService,
    private router: Router
  ) { 
    console.log("constructor RgStudentsComponent");
    this.gb = this.seGroupservice.gb;
    this.currentTerm = this.seGroupservice.gb.getSeGroup().currentTerm;
    if (! this.currentTerm) {
      this.router.navigate(['/start']);  
    }
    this.currentClass = this.seGroupservice.gb.getSeGroup().currentClass;
    if ( ! this.currentClass) {
      this.router.navigate(['/se-classes']);
    }
    this.achievements = this.currentClass.participations;
    if (this.achievements.length == 0) {
      this.router.navigate(['/student-edit']);  
    }
  }

  ngOnInit() {
  }

}
