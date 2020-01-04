import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { map, first, catchError } from 'rxjs/operators';
import { Router, ActivatedRoute } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class HomepageService {

  constructor(private http: HttpClient, private router: Router) { }

  testMethod(str: string) {
    console.log('Kliknuto na test dugme, a tekst je: ' + str);
    return this.http.get(environment.apiUrl + '/science-center-api/test/' + str)
    .pipe(map(responseMessage => responseMessage));
  }

}
