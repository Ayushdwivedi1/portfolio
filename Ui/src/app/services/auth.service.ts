import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from '../../environments/environment';

export interface LoginRequest {
  usernameOrEmail: string;
  password: string;
}

export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
  confirmPassword: string;
  firstName?: string;
  lastName?: string;
}

export interface AuthResponse {
  id: number;
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  lastLogin: string;
  message: string;
  token: string;
}

export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
  timestamp: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly API_URL = `${environment.apiUrl}/auth`;
  private currentUserSubject = new BehaviorSubject<AuthResponse | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {
    // Check if user is already logged in
    const user = this.getStoredUser();
    if (user) {
      this.currentUserSubject.next(user);
    }
  }

  login(credentials: LoginRequest): Observable<AuthResponse> {
    return this.http.post<ApiResponse<AuthResponse>>(`${this.API_URL}/login`, credentials)
      .pipe(
        map(response => {
          if (response.success && response.data) {
            this.storeUser(response.data);
            this.currentUserSubject.next(response.data);
            return response.data;
          } else {
            throw new Error(response.message || 'Login failed');
          }
        })
      );
  }

  register(userData: RegisterRequest): Observable<AuthResponse> {
    return this.http.post<ApiResponse<AuthResponse>>(`${this.API_URL}/register`, userData)
      .pipe(
        map(response => {
          if (response.success && response.data) {
            this.storeUser(response.data);
            this.currentUserSubject.next(response.data);
            return response.data;
          } else {
            throw new Error(response.message || 'Registration failed');
          }
        })
      );
  }

  logout(): Observable<string> {
    const user = this.getStoredUser();
    if (user) {
      return this.http.post<ApiResponse<string>>(`${this.API_URL}/logout?usernameOrEmail=${user.email}`, {})
        .pipe(
          map(response => {
            this.clearUser();
            this.currentUserSubject.next(null);
            return response.message || 'Logged out successfully';
          })
        );
    } else {
      this.clearUser();
      this.currentUserSubject.next(null);
      return new Observable(observer => {
        observer.next('Logged out successfully');
        observer.complete();
      });
    }
  }

  isLoggedIn(): boolean {
    return this.getStoredUser() !== null;
  }

  getCurrentUser(): AuthResponse | null {
    return this.currentUserSubject.value;
  }

  private storeUser(user: AuthResponse): void {
    localStorage.setItem('currentUser', JSON.stringify(user));
    localStorage.setItem('isLoggedIn', 'true');
  }

  private getStoredUser(): AuthResponse | null {
    const userStr = localStorage.getItem('currentUser');
    return userStr ? JSON.parse(userStr) : null;
  }

  private clearUser(): void {
    localStorage.removeItem('currentUser');
    localStorage.removeItem('isLoggedIn');
  }
} 