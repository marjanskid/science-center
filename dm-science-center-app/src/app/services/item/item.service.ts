import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { map, first, catchError } from 'rxjs/operators';
import { Router, ActivatedRoute } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  constructor(private http: HttpClient, private router: Router) { }

  getAllItems() {
    console.log('ItemService -> getAllItems');
    return this.http.get(environment.apiUrl + '/science-center-api/item/all')
    .pipe(map(allItems => allItems));
  }
}
