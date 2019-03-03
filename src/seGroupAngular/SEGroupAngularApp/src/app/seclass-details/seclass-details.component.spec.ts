import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SEClassDetailsComponent } from './seclass-details.component';

describe('SEClassDetailsComponent', () => {
  let component: SEClassDetailsComponent;
  let fixture: ComponentFixture<SEClassDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SEClassDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SEClassDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
