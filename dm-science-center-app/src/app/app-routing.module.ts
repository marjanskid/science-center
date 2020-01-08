import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomepageComponent } from './views/homepage/homepage.component';
import { AllItemsComponent } from './views/all-items/all-items.component';


const routes: Routes = [
  {
    path: 'home',
    component: HomepageComponent
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
