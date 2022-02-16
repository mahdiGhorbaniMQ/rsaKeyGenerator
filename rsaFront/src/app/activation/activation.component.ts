import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../auth.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {KeyService} from "../key.service";
import {UserService} from "../user.service";
import {ActivationService} from "../activation.service";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";

interface User{
  firstName:String,
  lastName:String,
  email:String,
  username:String,
}

@Component({
  selector: 'app-activation',
  templateUrl: './activation.component.html',
  styleUrls: ['./activation.component.scss']
})
export class ActivationComponent implements OnInit {

  form: FormGroup;
  userData!:User|null;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private _snackBar: MatSnackBar,
    private keyService: KeyService,
    private userService: UserService,
    private activationService:ActivationService,
    private http:HttpClient
  ) {
    userService.userData.subscribe(
      (userData)=>{
        this.userData = userData;
      }
    )
    this.form = this.fb.group({
      activationCode: ['', Validators.required]
    });
  }

  ngOnInit() {
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action,{
      duration: 3000,
    });
  }

  onSubmit(){
    if (this.form.valid) {
      var httpOptions={
        headers:new HttpHeaders({
          "Authorization" : "Bearer "+localStorage.getItem("token")
        })
      };
      const reqBody={
        "email":  this.activationService.email,
        "activationCode":  this.form.get('activationCode')?.value
      }
      if(this.activationService.state=="update"){
        this.http.post(environment.api+"/user/update/activate",reqBody,httpOptions).subscribe(
          (response:any)=>{
            this.openSnackBar("your account successfully was updated!","ok")
            this.userService.getUserDetails();
            this.router.navigate(["profile"]);
          },
          error => {
            this.openSnackBar("there is an error in updating user!","ok")
          }
        );
      }
      else if(this.activationService.state=="signup"){
        this.http.post(environment.api+"/auth/signup/activate",reqBody,httpOptions).subscribe(
          (response:any)=>{
            this.authService.login(this.activationService.username,this.activationService.password).subscribe(
              (response)=>{
                localStorage.setItem("token",response.token);
                this.openSnackBar("you successfully signed in!","ok");
                this.keyService.checkKeyStatus();
                this.authService.isAuthenticated.next(true);
                var user = {
                  firstName:response.firstName,
                  lastName:response.lastName,
                  email:response.email,
                  username:response.username
                }
                this.userService.userData.next(user)
                this.router.navigate(["/key"]);
              },
              error => {}
            );
          },
          error => {
            this.openSnackBar("there is an error in updating user!","ok")
          }
        );
      }
    }
  }

}
