import { Component, OnInit } from '@angular/core';
import { SegroupService } from '../segroup.service';
import { Router } from '@angular/router';
import { SEGroupBuilder } from '@SEGroup/segroup-model';
import SEClassFolder from 'libs/segroup-model/src/lib/SEClassFolder';

@Component({
  selector: 'SEGroup-rg-terms',
  templateUrl: './rg-terms.component.html',
  styleUrls: ['./rg-terms.component.css']
})
export class RgTermsComponent implements OnInit {
  public gb: SEGroupBuilder;
  public currentTerm: SEClassFolder;
  public terms: SEClassFolder[];

  constructor(
    private seGroupservice: SegroupService,
    private router: Router
  ) { 
    this.gb = this.seGroupservice.gb;
    this.currentTerm = this.seGroupservice.gb.getSeGroup().currentTerm;
    if (! this.currentTerm) {
      this.router.navigate(['/start']);  
    }
    console.log(this.currentTerm.name);

    this.terms = this.gb.getSeGroup().classFolder;
  }

  ngOnInit() {
    if (this.terms.length == 0) {
      this.router.navigate(['/start']);  
    }
  }

  gotoTerm(term: SEClassFolder) {
    this.gb.getSeGroup().currentTerm = term;
    this.router.navigate(['/se-classes']);
  }

}
