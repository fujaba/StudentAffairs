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

  constructor(
    private seGroupservice: SegroupService, 
    private router: Router
  ) { 
    this.gb = this.seGroupservice.gb;
  }

  ngOnInit() {
  }

}
