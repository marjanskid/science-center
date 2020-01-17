import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MagazineService {

  constructor(private httpClient: HttpClient) { }

  startNewMagazineProcess() {
    return this.httpClient.get(environment.apiUrl + '/magazine/getNewMagazineForm') as Observable<any>;
  }

  postNewMagazine(magazine, taskId) {
    console.log(taskId);
    return this.httpClient.post(environment.apiUrl + '/magazine/postNewMagazineForm/'.concat(taskId), magazine) as Observable<any>;
  }

  getReviewersAndEditorsForm() {
    return this.httpClient.get(environment.apiUrl + '/magazine/getReviewersAndEditorsForm') as Observable<any>;
  }

  postReviewersAndEditorsForm(taskId, magazineDetails) {
    return this.httpClient.post(environment.apiUrl +
      '/magazine/postReviewersAndEditorsForm/'.concat(taskId), magazineDetails) as Observable<any>;
  }

  getApproveMagazineForm() {
    return this.httpClient.get(environment.apiUrl + '/magazine/getApproveMagazineForm') as Observable<any>;
  }

  postApproveMagazineForm(taskId, magazineDetails) {
    return this.httpClient.post(environment.apiUrl +
      '/magazine/postApproveMagazineForm/'.concat(taskId), magazineDetails) as Observable<any>;
  }
}
