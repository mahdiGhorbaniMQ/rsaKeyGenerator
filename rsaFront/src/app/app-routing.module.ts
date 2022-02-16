import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { KeyPanleComponent } from './key-panle/key-panle.component';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import {ProfilePanelComponent} from "./profile-panel/profile-panel.component";
import {UpdateProfileComponent} from "./update-profile/update-profile.component";
import {ActivationComponent} from "./activation/activation.component";

const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'profile',
    component: ProfilePanelComponent
  },
  {
    path: 'profile/update',
    component: UpdateProfileComponent
  },
  {
    path: 'signup',
    component: SignupComponent
  },
  {
    path: 'key',
    component: KeyPanleComponent
  },
  {
    path: 'activation',
    component: ActivationComponent
  }
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
