import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ActivationService {

  public state!:String;
  public username!:String;
  public password!:String;
  public email!:String;
  constructor() { }
}
