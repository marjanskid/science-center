import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';

@Injectable()
export class Admin implements CanActivate {

  constructor() {}

  canActivate() {
    console.log('admin');
    return localStorage.getItem('sessionUserRole') === 'ADMIN';
  }
}