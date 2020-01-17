import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';

@Injectable()
export class Authorized implements CanActivate {

  constructor() {}

  canActivate() {
    console.log('autorized');
    if (localStorage.getItem('sessionUserRole')) {
      console.log('notautorized');
      return true;
    } else {
      return false;
    }
  }
}