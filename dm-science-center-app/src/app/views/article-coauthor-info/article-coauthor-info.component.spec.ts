import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ArticleCoauthorInfoComponent } from './article-coauthor-info.component';

describe('ArticleCoauthorInfoComponent', () => {
  let component: ArticleCoauthorInfoComponent;
  let fixture: ComponentFixture<ArticleCoauthorInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ArticleCoauthorInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArticleCoauthorInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
