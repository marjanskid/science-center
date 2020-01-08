import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomepageComponent } from './views/homepage/homepage.component';
import { TopNavbarComponent } from './views/top-navbar/top-navbar.component';
import { FooterComponent } from './views/footer/footer.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AllItemsComponent } from './views/all-items/all-items.component';

@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    TopNavbarComponent,
    FooterComponent,
    AllItemsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
