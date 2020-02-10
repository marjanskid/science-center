import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RepositoryService {

  categories = [];
  languages = [];
  books = [];

  constructor(private httpClient: HttpClient) { }

  getAllMyTasks(username) {
    return this.httpClient.get(environment.apiUrl + '/task/getAllMyTasks'.concat('/').concat(username)) as Observable<any>;
  }

  getMyNextTask(procesId, username) {
    alert('getMyNextTask');
    alert(username);
    return this.httpClient.get(environment.apiUrl + '/task/getTasks/'.concat(procesId).concat('/').concat(username)) as Observable<any>;
  }

  getTaskInfo(taskId: string) {
    alert('getTaskInfo');
    alert(taskId);
    return this.httpClient.get(environment.apiUrl + '/task/getTask/'.concat(taskId)) as Observable<any>;
  }

  postData(data, taskId) {
    return this.httpClient.post(environment.apiUrl + '/task/postTask/'.concat(taskId), data) as Observable<any>;
  }
}
