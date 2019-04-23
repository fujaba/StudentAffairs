import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RgSolutionEditComponent } from './rg-solution-edit.component';

describe('RgSolutionEditComponent', () => {
  let component: RgSolutionEditComponent;
  let fixture: ComponentFixture<RgSolutionEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RgSolutionEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RgSolutionEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
