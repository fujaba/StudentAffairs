import { Component, OnInit } from '@angular/core';
import SEClass from './../SEGroupModel/SEClass'
import {SegroupService} from '../segroup.service'

@Component({
  selector: 'app-segroup-terms',
  templateUrl: './segroup-terms.component.html',
  styleUrls: ['./segroup-terms.component.css']
})

export class SEGroupTermsComponent implements OnInit {
  
  constructor(private segroupService: SegroupService) { }
  
  seClassList: SEClass[];

  getClassList() {
    this.segroupService.getClassList()
      .subscribe(classList => this.seClassList = classList);
  }


  add(name: string): void {
    name = name.trim();
    if (!name) { return; }
    const newClass = new SEClass();
    newClass.topic = name;
    this.segroupService.addSEClass(newClass)
      .subscribe(elem => {
        this.seClassList.push(elem);
      });
  }


  delete(oldClass: SEClass): void {
    this.seClassList = this.seClassList.filter(h => h !== oldClass);
    this.segroupService.deleteSEClass(oldClass).subscribe();
  }


  ngOnInit() {
    this.getClassList();
  }

}
