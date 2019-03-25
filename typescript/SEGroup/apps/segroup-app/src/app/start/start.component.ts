import { Component, OnInit } from '@angular/core';
import { SegroupService } from '../segroup.service';
import { Router } from '@angular/router';
import { SEGroupBuilder } from '@SEGroup/segroup-model';

@Component({
  selector: 'SEGroup-start',
  templateUrl: './start.component.html',
  styleUrls: ['./start.component.css']
})
export class StartComponent implements OnInit {
  public gb: SEGroupBuilder;

  groupName: string;
  head: string;
  currentTerm: string;

  constructor(
    private seGroupservice: SegroupService, 
    private router: Router
  ) { 
    this.gb = this.seGroupservice.gb;
  }

  ngOnInit() {
    this.groupName = this.gb.getSeGroup().name;
    this.head = this.gb.getSeGroup().head;
    this.currentTerm = "";
    if (this.gb.getSeGroup().currentTerm) {
      this.currentTerm = this.gb.getSeGroup().currentTerm.name;
    }
  }

  okAction() {
    const term = this.gb.buildClassFolder(this.currentTerm);
    this.gb.buildGroup(this.groupName, this.head, term);
    // console.log(`input ${this.groupName} ${this.head} ${this.currentTerm}`);
    this.router.navigate(['/se-classes']);
  }

}
