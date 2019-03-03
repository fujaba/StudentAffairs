
import { Component, OnInit } from '@angular/core';
// import SECLass from '../SEGroupModel/SECLass';
import { SegroupService } from '../segroup.service';
 
interface ISEClass {
  topic: string;
  term: string;
}


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: [ './dashboard.component.css' ]
})
export class DashboardComponent implements OnInit {
  
  classList: ISEClass[] = [];
 
  constructor(private segroupService: SegroupService) { }
 
  ngOnInit() {
    this.getClassList();
  }
  
 
  getClassList(): void {
    this.segroupService.getClassList()
      .subscribe(classList => this.classList = classList);
  }
}