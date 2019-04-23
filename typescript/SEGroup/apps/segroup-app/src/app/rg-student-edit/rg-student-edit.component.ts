import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SegroupService } from '../segroup.service';
import { SEGroupBuilder } from '@SEGroup/segroup-model';
import SEClassFolder from 'libs/segroup-model/src/lib/SEClassFolder';
import SEClass from 'libs/segroup-model/src/lib/SEClass';

@Component({
  selector: 'SEGroup-rg-student-edit',
  templateUrl: './rg-student-edit.component.html',
  styleUrls: ['./rg-student-edit.component.css']
})
export class RgStudentEditComponent implements OnInit {
  public gb: SEGroupBuilder;
  public currentTerm: SEClassFolder;
  public currentClass: SEClass;
  
  public studentCSV = 'Alice\tm42\nBob\tm23\n';
  
  constructor(
    private seGroupservice: SegroupService,
    private router: Router
  ) { 
    console.log("constructor RgStudentEditComponent");
    this.gb = this.seGroupservice.gb;
    this.currentTerm = this.seGroupservice.gb.getSeGroup().currentTerm;
    if (! this.currentTerm) {
      this.router.navigate(['/start']);  
    }
    this.currentClass = this.seGroupservice.gb.getSeGroup().currentClass;
    if ( ! this.currentClass) {
      this.router.navigate(['/se-classes']);  
    }
  }

  ngOnInit() {
  }

  public okAction() {
    console.log('OK Action for new students\n'); 
    
    const lines = this.studentCSV.split('\n');

    for (const l of lines) {
      console.log('found line: ' + l);
      if ( l.search(/[0-9]/) < 0) {
        // no digits, no student id, omit
        continue;    
      }
      
      const words = l.split(/[ \t]/);
      let phase = 'skip prefix';
      let name = '';
      let studentId = '';
      let email = '';
      for (const w of words) {
        if (phase === 'skip prefix') {
          // ignore leading dates or row numbers
          if (w.search(/[0-9]/) >= 0) {
            continue;
          }
          phase = 'name';
        }

        if (phase === 'name') {
          if (w.search(/[0-9]/) < 0) {
            name += w + ' ';
          }
          else {
            phase = 'id';
          }
        }

        if (phase === 'id') {
          studentId = w;
          phase = 'email';
          continue;
        }

        if (phase === 'email') {
          if (w.indexOf('@') > 0) {
            email = w;
            break;
          }
        }
      }

      name = name.trim();

      console.log(`found: ${name} | ${studentId} | ${email}`);
      this.gb.buildAchievementByNameId(name, studentId, email, this.currentClass);

    }

    
    this.router.navigate(['/assignments']);
  }
}
