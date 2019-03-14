import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SeClassEditComponent } from './se-class-edit.component';

describe('SeClassEditComponent', () => {
  let component: SeClassEditComponent;
  let fixture: ComponentFixture<SeClassEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SeClassEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SeClassEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
