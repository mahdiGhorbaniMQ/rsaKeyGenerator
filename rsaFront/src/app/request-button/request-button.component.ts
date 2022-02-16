import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

interface States{
  task:String;
  done:String;
  loading:String;
}

@Component({
  selector: 'app-request-button',
  templateUrl: './request-button.component.html',
  styleUrls: ['./request-button.component.scss']
})
export class RequestButtonComponent implements OnInit {

  constructor() { }

  @Input("states") states!:States;
  @Input("status") status!:String;

  @Output("onClick") eventEmitter = new EventEmitter();
  
  ngOnInit(): void {
  }

  onClick():void{
    this.eventEmitter.emit("clicked");
  }
}
