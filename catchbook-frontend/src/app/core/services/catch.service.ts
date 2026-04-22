import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Catch } from '../models/catch.model';

@Injectable({
  providedIn: 'root'
})
export class CatchService {

  private apiUrl = 'http://localhost:8080/api/catches';

  constructor(private http: HttpClient) { }

  getAllCatches(): Observable<Catch[]> {
    return this.http.get<Catch[]>(this.apiUrl);
  }

  getCatchById(id: number): Observable<Catch> {
    return this.http.get<Catch>(`${this.apiUrl}/${id}`);
  }

  getCatchesByUser(userId: number): Observable<Catch[]> {
    return this.http.get<Catch[]>(`${this.apiUrl}/user/${userId}`);
  }

  saveCatch(catch_: Catch): Observable<Catch> {
    return this.http.post<Catch>(this.apiUrl, catch_);
  }

  deleteCatch(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
