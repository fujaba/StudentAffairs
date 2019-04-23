import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RgStudentEditComponent } from './rg-student-edit.component';

describe('RgStudentEditComponent', () => {
  let component: RgStudentEditComponent;
  let fixture: ComponentFixture<RgStudentEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RgStudentEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RgStudentEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
