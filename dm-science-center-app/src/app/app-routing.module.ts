import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomepageComponent } from './views/homepage/homepage.component';
import { AllItemsComponent } from './views/all-items/all-items.component';
import { SignInComponent } from './views/sign-in/sign-in.component';
import { SignUpComponent } from './views/sign-up/sign-up.component';


const routes: Routes = [
  {
    path: 'home',
    component: HomepageComponent
  },
  {
    path: 'sign-in',
    component: SignInComponent
  },
  {
    path: 'sign-up',
    component: SignUpComponent
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
