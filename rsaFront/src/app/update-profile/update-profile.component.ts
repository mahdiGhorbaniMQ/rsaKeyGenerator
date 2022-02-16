import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../auth.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {KeyService} from "../key.service";
import {UserService} from "../user.service";
import {ActivationService} from "../activation.service";

interface User{
  firstName:String,
  lastName:String,
  email:String,
  username:String,
}
@Component({
  selector: 'app-update-profile',
  templateUrl: './update-profile.component.html',
  styleUrls: ['./update-profile.component.scss']
})
export class UpdateProfileComponent implements OnInit {

  form: FormGroup;

  userData!:User|null;

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
  ) {
    userService.userData.subscribe(
      (userData)=>{
        this.userData = userData;
      }
    )
    this.form = this.fb.group({
      firstName: [this.userData!.firstName, [Validators.minLength(3),Validators.required]],
      lastName: [this.userData!.lastName],
      email: [this.userData!.email, [Validators.email,Validators.required]],
      username: [this.userData!.username, Validators.required],
    });
  }

  ngOnInit(){
  }

  onSubmit(){
    if (this.form.valid) {
      const updateData={
        "firstName": this.form.get('firstName')?.value,
        "lastName": this.form.get('lastName')?.value,
        "email": this.form.get('email')?.value,
      }
      this.userService.update(updateData);
    }
  }

}
