
import { Component, OnInit } from '@angular/core';
 
import { Observable, Subject } from 'rxjs';
 
import {
   debounceTime, distinctUntilChanged, switchMap
 } from 'rxjs/operators';
 
import SEClass from '../SEGroupModel/SEClass';
import { SegroupService } from '../segroup.service';
 

@Component({
  selector: 'app-seclass-search',
  templateUrl: './seclass-search.component.html',
  styleUrls: ['./seclass-search.component.css']
})
export class SeclassSearchComponent implements OnInit {
 
  classList$: Observable<SEClass[]>;

  private searchTerms = new Subject<string>();
 
  constructor(private segroupService: SegroupService) {}
 
  // Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term);
  }
 
  ngOnInit(): void {
    this.classList$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),
 
      // ignore new term if same as previous term
      distinctUntilChanged(),
 
      // switch to new search observable each time the term changes
      switchMap((term: string) => this.segroupService.searchClassList(term)),
    );
  }
}
