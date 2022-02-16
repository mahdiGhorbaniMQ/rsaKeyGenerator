import { Component } from '@angular/core';
import { AuthService } from './auth.service';
import {Router} from "@angular/router";
import {KeyService} from "./key.service";
import {HttpClient} from "@angular/common/http";
import {UserService} from "./user.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'Secure Filetransport';
  isAuthenticated:Boolean = false;
  constructor(public authService: AuthService,
              private router: Router,
              private keyService: KeyService,
              private userService:UserService) {
  }

  ngOnInit(){
    this.authService.isAuthenticated.subscribe(
      (isAuthenticated:Boolean)=>{
        this.isAuthenticated = isAuthenticated;
        if(isAuthenticated){
          this.userService.getUserDetails();
        }
      }
    )
    this.keyService.checkKeyStatus();
  }

  signOut(){
    localStorage.removeItem("token");
    this.authService.isAuthenticated.next(false);
    this.router.navigate([""]);
  }
}
