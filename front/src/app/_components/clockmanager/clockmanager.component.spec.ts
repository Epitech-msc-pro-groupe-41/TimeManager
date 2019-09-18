import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ClockmanagerComponent } from './clockmanager.component';

describe('ClockmanagerComponent', () => {
  let component: ClockmanagerComponent;
  let fixture: ComponentFixture<ClockmanagerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ClockmanagerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClockmanagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
