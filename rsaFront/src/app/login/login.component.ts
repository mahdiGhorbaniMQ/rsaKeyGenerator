import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../auth.service';
import {KeyService} from "../key.service";
import {UserService} from "../user.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private _snackBar: MatSnackBar,
    private keyService: KeyService,
    private userService: UserService,
  ) {

    this.form = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
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
      const username = this.form.get('username')?.value;
      const password = this.form.get('password')?.value;
      this.authService.login(username,password).subscribe(
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
        (error)=>{
          this.openSnackBar("username or password is incorrect!","ok")
        }
      );
    }
  }

  goToSignupPage(){
    this.router.navigate(["/signup"])
  }
}
