import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RgStudentsComponent } from './rg-students.component';

describe('RgStudentsComponent', () => {
  let component: RgStudentsComponent;
  let fixture: ComponentFixture<RgStudentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RgStudentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RgStudentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
