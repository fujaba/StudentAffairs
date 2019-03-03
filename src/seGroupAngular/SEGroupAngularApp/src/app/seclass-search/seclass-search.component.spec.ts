import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SeclassSearchComponent } from './seclass-search.component';

describe('SeclassSearchComponent', () => {
  let component: SeclassSearchComponent;
  let fixture: ComponentFixture<SeclassSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SeclassSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SeclassSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
