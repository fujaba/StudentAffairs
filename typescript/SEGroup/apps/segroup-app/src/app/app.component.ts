import { Component, OnInit } from '@angular/core';
import { SegroupService } from './segroup.service';
import { SEGroupBuilder } from '@SEGroup/segroup-model';
import { Router } from '@angular/router';

@Component({
  selector: 'SEGroup-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'research-group-app';
  public gb: SEGroupBuilder;
  
  serverResult = {'yaml': '- text: loading ...'};

  constructor(
    private seGroupservice: SegroupService,
    private router: Router
     ) { 
      this.gb = this.seGroupservice.gb;
      console.log('constructor on root');
     }

  public ngOnInit() { 
    console.log('onInit on root');
    if ( ! this.seGroupservice.gb.getSeGroup().currentTerm) {
      this.router.navigate(['/start']);
    } else {
      this.seGroupservice.currentTerm = this.seGroupservice.gb.getSeGroup().currentTerm.name;
      this.router.navigate(['/se-classes']);
    } 
    
  }

  uploadAction() {
    this.seGroupservice.uploadAction();
  }

  downloadAction() {
    this.seGroupservice.downloadAction();
  }
  syncAction() {
    this.seGroupservice.syncAction();
  }
}
