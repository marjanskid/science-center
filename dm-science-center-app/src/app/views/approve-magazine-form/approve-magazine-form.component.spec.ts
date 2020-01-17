import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApproveMagazineFormComponent } from './approve-magazine-form.component';

describe('ApproveMagazineFormComponent', () => {
  let component: ApproveMagazineFormComponent;
  let fixture: ComponentFixture<ApproveMagazineFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApproveMagazineFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApproveMagazineFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
