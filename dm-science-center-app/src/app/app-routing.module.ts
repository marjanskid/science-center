import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomepageComponent } from './views/homepage/homepage.component';
import { AllItemsComponent } from './views/all-items/all-items.component';
import { SignInComponent } from './views/sign-in/sign-in.component';
import { SignUpComponent } from './views/sign-up/sign-up.component';
import { ReviewerFormComponent } from './views/reviewer-form/reviewer-form.component';
import { NewMagazineFormComponent } from './views/new-magazine-form/new-magazine-form.component';
import { AddReviewersAndEditorsComponent } from './views/add-reviewers-and-editors/add-reviewers-and-editors.component';
import { ApproveMagazineFormComponent } from './views/approve-magazine-form/approve-magazine-form.component';
import { Notauthorized } from './guards/notauthorized.guard';
import { Admin } from './guards/admin.guard';
import { Authorized } from './guards/authorized.guard';

const routes: Routes = [
  {
    path: 'home',
    component: HomepageComponent
  },
  {
    path: 'sign-in',
    component: SignInComponent,
    canActivate: [Notauthorized]
  },
  {
    path: 'sign-up',
    component: SignUpComponent,
    canActivate: [Notauthorized]
  },
  {
    path: 'approve-reviewer',
    component: ReviewerFormComponent,
    canActivate: [Admin]
  },
  {
    path: 'new-magazine',
    component: NewMagazineFormComponent,
    canActivate: [Authorized]
  },
  {
    path: 'add-reviewers-and-editors',
    component: AddReviewersAndEditorsComponent,
    canActivate: [Authorized]
  },
  {
    path: 'approve-magazine',
    component: ApproveMagazineFormComponent,
    canActivate: [Admin]
  },
  {
    path: 'all-items',
    children: [
      { path: '', component: AllItemsComponent },
    ]
  },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
    RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
