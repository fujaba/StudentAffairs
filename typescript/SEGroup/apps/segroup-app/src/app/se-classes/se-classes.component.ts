import { Component, OnInit } from '@angular/core';
import { SegroupService } from '../segroup.service';
import { SEGroupBuilder } from '@SEGroup/segroup-model';
import SEClass from 'libs/segroup-model/src/lib/SEClass';

@Component({
  selector: 'SEGroup-se-classes',
  templateUrl: './se-classes.component.html',
  styleUrls: ['./se-classes.component.css']
})
export class SeClassesComponent implements OnInit {
  public gb: SEGroupBuilder;
  public classes: SEClass[];

  constructor(private seGroupservice: SegroupService) { 
    // console.log("init SeClassesComponent");
    this.gb = this.seGroupservice.gb;
    this.classes = this.seGroupservice.gb.getSeGroup().classes;
  }

  ngOnInit() {
  }

  public removeSEClass(seClass: SEClass): void {
    this.gb.removeSEClass(seClass);
  }
}
