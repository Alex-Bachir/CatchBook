import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Spot } from '../models/spot.model';

@Injectable({
  providedIn: 'root'
})
export class SpotService {

  private apiUrl = 'http://localhost:8080/api/spots';

  constructor(private http: HttpClient) { }

  getAllSpots(): Observable<Spot[]> {
    return this.http.get<Spot[]>(this.apiUrl);
  }

  getSpotById(id: number): Observable<Spot> {
    return this.http.get<Spot>(`${this.apiUrl}/${id}`);
  }

  getSpotsByUser(userId: number): Observable<Spot[]> {
    return this.http.get<Spot[]>(`${this.apiUrl}/user/${userId}`);
  }

  saveSpot(spot: Spot): Observable<Spot> {
    return this.http.post<Spot>(this.apiUrl, spot);
  }

  deleteSpot(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
