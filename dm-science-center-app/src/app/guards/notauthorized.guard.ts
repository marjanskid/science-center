import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';

@Injectable()
export class Notauthorized implements CanActivate {

  constructor() {}

  canActivate() {
    console.log('notautorized');
    if (!localStorage.getItem('sessionUserRole')) {
      console.log('true');
      return true;
    } else {
      return false;
    }
  }
}