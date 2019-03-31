import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RgTermsComponent } from './rg-terms.component';

describe('RgTermsComponent', () => {
  let component: RgTermsComponent;
  let fixture: ComponentFixture<RgTermsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RgTermsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RgTermsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
