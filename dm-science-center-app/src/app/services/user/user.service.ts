import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) { }

  startRegistrationProcess() {
    return this.httpClient.get(environment.apiUrl + '/registration/getRegistrationForm') as Observable<any>;
  }

  registerUser(user, taskId) {
    return this.httpClient.post(environment.apiUrl + '/registration/postForm/'.concat(taskId), user) as Observable<any>;
  }

  getReviewerForm() {
    return this.httpClient.get(environment.apiUrl + '/registration/getReviewerForm') as Observable<any>;
  }

  putConfirmReviewer(taskId, user) {
    return this.httpClient.post(environment.apiUrl + '/registration/postReviewerForm/'.concat(taskId), user) as Observable<any>;
  }

  signin(username: string, password: string) {
    return this.httpClient.post<any>(environment.apiUrl + '/user/sign-in', {
      username, password
    }).pipe(map(user => {
      if (user) {
        localStorage.setItem('sessionUser', JSON.stringify(user));
      }
      return user;
    }
    ));
  }
}
