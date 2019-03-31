import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RgAssignmentsComponent } from './rg-assignments.component';

describe('RgAssignmentsComponent', () => {
  let component: RgAssignmentsComponent;
  let fixture: ComponentFixture<RgAssignmentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RgAssignmentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RgAssignmentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
