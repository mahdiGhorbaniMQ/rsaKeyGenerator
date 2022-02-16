import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { KeyService } from '../key.service';

enum changeStates {
  task = "change",
  loading = "changing",
  done = "changed"
}
enum generateStates {
  task = "generate",
  loading = "generating",
  done = "generated"
}

@Component({
  selector: 'app-key-panle',
  templateUrl: './key-panle.component.html',
  styleUrls: ['./key-panle.component.scss']
})
export class KeyPanleComponent implements OnInit {

  userKeyStatus:String = "";

  generateStatus:String = generateStates.task;
  generateStates={
    task: generateStates.task,
    loading: generateStates.loading,
    done: generateStates.done
  };

  changeStatus:String = changeStates.task;
  changeStates={
    task: changeStates.task,
    loading: changeStates.loading,
    done: changeStates.done
  };

  constructor(
    private _snackBar: MatSnackBar,
    private keyService: KeyService,
    private router: Router) {}

  ngOnInit(): void {
    if(!localStorage.getItem("token")){
      this.router.navigate(["login"]);
    }
    else{
      this.keyService.getUserKeyStatus().subscribe(
        (userKeyStatus:any)=>{
          this.userKeyStatus = userKeyStatus.message;
        },
        error=>{}
      )
    }
  }
  ngAfterContentInit(){
    this.ngOnInit();
  }
  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action,{
      duration: 3000,
    });
  }
  generate(){
    this.generateStatus = generateStates.loading;
    this.keyService.generateUserKey().subscribe(
      (response)=>{
        this.generateStatus = generateStates.done;
        this.openSnackBar("your key was generated!","ok")
      },
      (error)=>{
        this.generateStatus = generateStates.task;
        this.openSnackBar("there is an error in generating!","ok")
      }
    );
  }
  change(){
    this.changeStatus = changeStates.loading;
    this.keyService.changeUserKey().subscribe(
      (response)=>{
        this.changeStatus = changeStates.done;
        this.openSnackBar("your key was changed!","ok")
      },
      (error)=>{
        this.changeStatus = changeStates.task;
        this.openSnackBar("there is an error in changing key!","ok")
      }
    );
  }
}
