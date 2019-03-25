import { Component, OnInit } from '@angular/core';
import { Router }          from '@angular/router';
import { SEGroupBuilder } from '@SEGroup/segroup-model';
import { SegroupService } from '../segroup.service';
import { getBindingRoot } from '@angular/core/src/render3/state';
import SEClassFolder from 'libs/segroup-model/src/lib/SEClassFolder';

@Component({
  selector: 'SEGroup-se-class-edit',
  templateUrl: './se-class-edit.component.html',
  styleUrls: ['./se-class-edit.component.css']
})
export class SeClassEditComponent implements OnInit {
  public topic: string = "";
  public currentTerm: SEClassFolder;
  public term: string = "";

  public gb: SEGroupBuilder;
  
  constructor(
    private seGroupservice: SegroupService, 
    private router: Router
    ) { 
    this.gb = this.seGroupservice.gb;
    this.currentTerm = this.seGroupservice.gb.getSeGroup().currentTerm;
    this.term = this.currentTerm.name;
  }

  ngOnInit() {
  }

  public okAction() {
    if (this.seGroupservice.currentSEClass === undefined) {
      console.log(` got ${this.topic} ${this.term}` );
      this.gb.buildSEClass(this.topic, this.currentTerm);
      
      // this.seGroupservice.currentSEClass = this.gb.buildSEClass(this.topic, this.term);
    }
    
    this.router.navigate(['/se-classes']);
  }

  public cancelAction() {
    this.router.navigate(['/se-classes']);
  }
}
