
import { Component, OnInit, Input } from '@angular/core';
import SEClass from "./../SEGroupModel/SEClass";
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { SegroupService } from "../segroup.service";


@Component({
  selector: 'app-seclass-details',
  templateUrl: './seclass-details.component.html',
  styleUrls: ['./seclass-details.component.css']
})

export class SEClassDetailsComponent implements OnInit {

  currentClass: SEClass = {} as SEClass;

  constructor(
    private route: ActivatedRoute,
    private seGroupService: SegroupService,
    private location: Location
    ) { }

  ngOnInit() {
    this.getCurrentClass();
  }


  getCurrentClass(): void {
    const id : string = this.route.snapshot.paramMap.get('id');
    this.seGroupService.getSEClass(id)
      .subscribe(theClass => this.currentClass = theClass);
  }


  save(): void {
    this.seGroupService.updateSEClass(this.currentClass)
      .subscribe(() => this.goBack());
  }

  goBack(): void {
    this.location.back();
  }
}
