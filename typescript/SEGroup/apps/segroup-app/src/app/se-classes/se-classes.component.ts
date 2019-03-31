import { Component, OnInit } from '@angular/core';
import { SegroupService } from '../segroup.service';
import { SEGroupBuilder } from '@SEGroup/segroup-model';
import SEClass from 'libs/segroup-model/src/lib/SEClass';
import SEClassFolder from 'libs/segroup-model/src/lib/SEClassFolder';
import { Router } from '@angular/router';

@Component({
  selector: 'SEGroup-se-classes',
  templateUrl: './se-classes.component.html',
  styleUrls: ['./se-classes.component.css']
})
export class SeClassesComponent implements OnInit {
  public gb: SEGroupBuilder;
  public currentTerm: SEClassFolder;
  public classes: SEClass[];

  constructor(
    private seGroupservice: SegroupService,
    private router: Router
    ) { 
    console.log("constructor SeClassesComponent");
    this.gb = this.seGroupservice.gb;
    this.currentTerm = this.seGroupservice.gb.getSeGroup().currentTerm;
    if (! this.currentTerm) {
      this.router.navigate(['/start']);  
    }
    this.classes = this.seGroupservice.gb.getSeGroup().currentTerm.classes;
  }

  ngOnInit() {
    console.log('nginit on SEClassComponent');
    if (this.classes.length == 0) {
      this.router.navigate(['/se-class-edit']);  
    }
  }

  public gotoAssignments(seClass: SEClass) {
    this.gb.getSeGroup().currentClass = seClass;
    this.router.navigate(['/assignments']);
  }

  public removeSEClass(seClass: SEClass): void {
    this.gb.removeSEClass(seClass);
  }
}
