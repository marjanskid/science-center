import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ArticleBasicInfoComponent } from './article-basic-info.component';

describe('ArticleBasicInfoComponent', () => {
  let component: ArticleBasicInfoComponent;
  let fixture: ComponentFixture<ArticleBasicInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ArticleBasicInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArticleBasicInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
