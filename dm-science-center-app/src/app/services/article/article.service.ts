import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  constructor(private httpClient: HttpClient, private router: Router) { }

  startArticleProcess(user) {
    return this.httpClient.get(environment.apiUrl + '/article/getNewArticleForm'.concat('/').concat(user)) as Observable<any>;
  }

  postNewArticle(article, taskId) {
    console.log(article);
    return this.httpClient.post(environment.apiUrl + '/article/postNewArticleForm/'.concat(taskId), article) as Observable<any>;
  }

  postArticleAuthorBasicInfo(article, taskId) {
    console.log(article);
    return this.httpClient.post(environment.apiUrl + '/article/postArticleAuthorBasicInfo/'.concat(taskId), article) as Observable<any>;
  }

  postArticleCoauthorInfo(article, taskId) {
    console.log(article);
    return this.httpClient.post(environment.apiUrl + '/article/postArticleCoauthorInfo/'.concat(taskId), article) as Observable<any>;
  }
}
