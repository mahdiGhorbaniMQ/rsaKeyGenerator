import { Component, OnInit } from '@angular/core';
import {AuthService} from "../auth.service";
import {UserService} from "../user.service";
import {Router} from "@angular/router";

interface User{
  firstName:String,
  lastName:String,
  email:String,
  username:String,
}

@Component({
  selector: 'app-profile-panel',
  templateUrl: './profile-panel.component.html',
  styleUrls: ['./profile-panel.component.scss']
})
export class ProfilePanelComponent implements OnInit {

  user!:User | null;
  constructor(
    private userService:UserService,
    private router:Router
  ) { }

  ngOnInit(): void {
    this.userService.userData.subscribe(
      (userData)=>{
        this.user = userData;
      }
    )
  }
  goToUpdatePanel(){
    this.router.navigate(["profile/update"]);
  }
}
