import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private router: Router) { }

  signin(username: string, password: string) {
    return this.http.post<any>(environment.apiUrl + '/science-center-api/user/sign-in', {
      username, password
    }).pipe(map(user => {
      if (user && user.token) {
        localStorage.setItem('sessionUser', JSON.stringify(user));
      }
      return user;
    }
    ));
  }

  signup(name: string, lastname: string, email: string, username: string, password: string, rePassword: string) {
    return this.http.post<any>(environment.apiUrl + '/science-center-api/user/sign-up', {
      username, password, lastname, email, rePassword, name
    }).pipe(map(message => {
      return message;
    }
    ));
  }

  logout() {
    localStorage.removeItem('sessionUser');
    // localStorage.removeItem('order');
  }
}
