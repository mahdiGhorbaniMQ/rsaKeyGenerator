import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { environment } from 'src/environments/environment';


interface SignupModel{
  firstName:String,
  lastName:String,
  email:String,
  username:String,
  password:String
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public isAuthenticated:BehaviorSubject<Boolean> = new BehaviorSubject<Boolean>(false);

  constructor(private http: HttpClient) {
  }

  login(username: String, password: String): Observable<any> {
    var loginData={
      "username": username,
      "password": password
    };
    return this.http.post(environment.api+"/auth/login",loginData)
  }
  signup(signupData:SignupModel): Observable<any> {
    return this.http.post(environment.api+"/auth/signup",signupData);
  }

}

