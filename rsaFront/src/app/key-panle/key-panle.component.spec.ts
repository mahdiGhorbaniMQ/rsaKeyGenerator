import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KeyPanleComponent } from './key-panle.component';

describe('KeyPanleComponent', () => {
  let component: KeyPanleComponent;
  let fixture: ComponentFixture<KeyPanleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ KeyPanleComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(KeyPanleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
