import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import {AuthService} from "./auth.service";

@Injectable({
  providedIn: 'root'
})
export class KeyService {

  private userKeyStatus:BehaviorSubject<String> = new BehaviorSubject<String>("noKey");
  constructor(
    private http:HttpClient,
    private authService:AuthService,
  ) {
    this.checkKeyStatus();
  }

  public getUserKeyStatus():Observable<String>{
    return this.userKeyStatus.asObservable();
  }
  public generateUserKey():Observable<any>{
    var httpOptions={
      headers:new HttpHeaders({
        "Authorization" : "Bearer "+localStorage.getItem("token")
      })
    };
    return this.http.get(environment.api+"/key/generate",httpOptions);
  }
  public changeUserKey():Observable<any>{
    var httpOptions={
      headers:new HttpHeaders({
        "Authorization" : "Bearer "+localStorage.getItem("token")
      })
    };
    return this.http.get(environment.api+"/key/change",httpOptions);
  }

  public checkKeyStatus(){
    var httpOptions={
      headers:new HttpHeaders({
        "Authorization" : "Bearer "+localStorage.getItem("token")
      })
    };
    this.http.get(environment.api+"/key/status",httpOptions).subscribe(
      (keyStatus:any)=>{
        this.userKeyStatus.next(keyStatus);
        this.authService.isAuthenticated.next(true);
      }
    );
  }
}
