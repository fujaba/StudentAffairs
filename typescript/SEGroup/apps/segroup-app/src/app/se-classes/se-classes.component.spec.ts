import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SeClassesComponent } from './se-classes.component';

describe('SeClassesComponent', () => {
  let component: SeClassesComponent;
  let fixture: ComponentFixture<SeClassesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SeClassesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SeClassesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
