import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

const BASIC_URL = "http://localhost:8080/";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  register(signupRequest: any): Observable<any> {
    const headers = { 'Content-Type': 'application/json' };
    return this.http.post(BASIC_URL + "sign-up", signupRequest, { headers }).pipe(
      catchError(error => {
        console.error('Signup failed:', error.error); // Log the error response
        return throwError(error); // Re-throw the error
      })
    );
  }
}