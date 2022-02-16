import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { KeyPanleComponent } from './key-panle/key-panle.component';
import { HomeComponent } from './home/home.component';
import { MaterialModule } from './material/material.module';
import { RequestButtonComponent } from './request-button/request-button.component';
import { ProfilePanelComponent } from './profile-panel/profile-panel.component';
import { UpdateProfileComponent } from './update-profile/update-profile.component';
import { ActivationComponent } from './activation/activation.component';
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent,
    KeyPanleComponent,
    RequestButtonComponent,
    HomeComponent,
    ProfilePanelComponent,
    UpdateProfileComponent,
    ActivationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    MaterialModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
