import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule } from '@angular/forms';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomepageComponent } from './views/homepage/homepage.component';
import { TopNavbarComponent } from './views/top-navbar/top-navbar.component';
import { FooterComponent } from './views/footer/footer.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AllItemsComponent } from './views/all-items/all-items.component';
import { SignInComponent } from './views/sign-in/sign-in.component';
import { SignUpComponent } from './views/sign-up/sign-up.component';
import { ReviewerFormComponent } from './views/reviewer-form/reviewer-form.component';
import { NewMagazineFormComponent } from './views/new-magazine-form/new-magazine-form.component';
import { AddReviewersAndEditorsComponent } from './views/add-reviewers-and-editors/add-reviewers-and-editors.component';
import { ApproveMagazineFormComponent } from './views/approve-magazine-form/approve-magazine-form.component';
import { Authorized } from '../app/guards/authorized.guard';
import { Admin } from '../app/guards/admin.guard';
import { Notauthorized } from '../app/guards/notauthorized.guard';

@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    TopNavbarComponent,
    FooterComponent,
    AllItemsComponent,
    SignInComponent,
    SignUpComponent,
    ReviewerFormComponent,
    NewMagazineFormComponent,
    AddReviewersAndEditorsComponent,
    ApproveMagazineFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    BrowserModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [Notauthorized, Authorized, Admin],
  bootstrap: [AppComponent]
})
export class AppModule { }
