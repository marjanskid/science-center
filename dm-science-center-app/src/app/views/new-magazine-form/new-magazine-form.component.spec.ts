import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewMagazineFormComponent } from './new-magazine-form.component';

describe('NewMagazineFormComponent', () => {
  let component: NewMagazineFormComponent;
  let fixture: ComponentFixture<NewMagazineFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewMagazineFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewMagazineFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
