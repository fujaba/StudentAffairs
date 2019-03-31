import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RgAssignmentEditComponent } from './rg-assignment-edit.component';

describe('RgAssignmentEditComponent', () => {
  let component: RgAssignmentEditComponent;
  let fixture: ComponentFixture<RgAssignmentEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RgAssignmentEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RgAssignmentEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
