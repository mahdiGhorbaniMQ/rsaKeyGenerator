import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../environments/environment";
import {BehaviorSubject} from "rxjs";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ActivationService} from "./activation.service";

interface User{
  firstName:String,
  lastName:String,
  email:String,
  username:String,
}

@Injectable({
  providedIn: 'root'
})
export class UserService {

  public userData:BehaviorSubject<User|null> = new BehaviorSubject<User|null>(null);

  constructor(
    private http:HttpClient,
    private router:Router,
    private _snackBar: MatSnackBar,
    private activationService:ActivationService
  ) { }
  getUserDetails(){
    var httpOptions={
      headers:new HttpHeaders({
        "Authorization" : "Bearer "+localStorage.getItem("token")
      })
    };
    this.http.get(environment.api+"/user/details",httpOptions).subscribe(
      (userData:any)=>{
        this.userData.next(userData);
      }
    );
  }
  update(userData:any){
    var httpOptions={
      headers:new HttpHeaders({
        "Authorization" : "Bearer "+localStorage.getItem("token")
      })
    };
    this.http.put(environment.api+"/user/update",userData,httpOptions).subscribe(
      (response:any)=>{
        this.activationService.state="update";
        this.activationService.email=userData.email
        this.openSnackBar("Activation code send to your new email!","ok")
        this.router.navigate(["activation"]);
        // this.getUserDetails();
        // this.router.navigate(["profile"]);
      },
      error => {
        this.openSnackBar("there is an error in updating user!","ok")
      }
    );
  }
  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action,{
      duration: 3000,
    });
  }
}
