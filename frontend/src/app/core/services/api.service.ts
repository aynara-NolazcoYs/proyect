import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Student, Promoter, Enrollment, DashboardData } from '@app/shared/models';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private apiBase = 'http://localhost:3000/api';

  constructor(private http: HttpClient) {}

  // Dashboard
  getDashboard(): Observable<DashboardData> {
    return this.http.get<DashboardData>(`${this.apiBase}/dashboard`);
  }

  // Students
  getStudents(): Observable<Student[]> {
    return this.http.get<Student[]>(`${this.apiBase}/students`);
  }

  // Promoters
  getPromoters(): Observable<Promoter[]> {
    return this.http.get<Promoter[]>(`${this.apiBase}/promoters`);
  }

  // Enrollments
  getEnrollments(): Observable<Enrollment[]> {
    return this.http.get<Enrollment[]>(`${this.apiBase}/enrollments`);
  }

  createEnrollment(enrollment: Enrollment): Observable<any> {
    return this.http.post(`${this.apiBase}/enrollments`, enrollment);
  }
}
