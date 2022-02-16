import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../auth.service';
import {KeyService} from "../key.service";
import {UserService} from "../user.service";
import {ActivationService} from "../activation.service";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {

  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private _snackBar: MatSnackBar,
    private keyService: KeyService,
    private userService: UserService,
    private activationService:ActivationService
  ) {

    this.form = this.fb.group({
      firstName: ['', [Validators.minLength(3),Validators.required]],
      lastName: [''],
      email: ['', [Validators.email,Validators.required]],
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  ngOnInit(){
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action,{
      duration: 3000,
    });
  }

  onSubmit(){
    if (this.form.valid) {
      const signupData={
        "firstName": this.form.get('firstName')?.value,
        "lastName": this.form.get('lastName')?.value,
        "email": this.form.get('email')?.value,
        "username": this.form.get('username')?.value,
        "password": this.form.get('password')?.value,
      }
      this.authService.signup(signupData).subscribe(
        (signupResponse)=>{
          this.activationService.state="signup";
          this.activationService.username = signupData.username;
          this.activationService.password = signupData.password;
          this.activationService.email = signupData.email;
          this.openSnackBar("Activation code send to your new email!","ok")
          this.router.navigate(["activation"]);
        },
        (error)=>{
          console.log(error)
          this.openSnackBar("there is an error in create your account!","ok")
        }
      );
    }
  }

  goToLoginPage(){
    this.router.navigate(["/login"])
  }
}
