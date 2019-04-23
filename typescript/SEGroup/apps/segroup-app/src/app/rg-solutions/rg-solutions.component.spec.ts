import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RgSolutionsComponent } from './rg-solutions.component';

describe('RgSolutionsComponent', () => {
  let component: RgSolutionsComponent;
  let fixture: ComponentFixture<RgSolutionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RgSolutionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RgSolutionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
