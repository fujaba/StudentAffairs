import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SEGroupTermsComponent } from './segroup-terms.component';

describe('SEGroupTermsComponent', () => {
  let component: SEGroupTermsComponent;
  let fixture: ComponentFixture<SEGroupTermsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SEGroupTermsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SEGroupTermsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
