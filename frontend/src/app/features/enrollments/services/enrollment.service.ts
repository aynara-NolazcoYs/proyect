import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Enrollment } from '@app/shared/models';
import { ApiService } from '@app/core/services';

@Injectable({
  providedIn: 'root'
})
export class EnrollmentService {
  constructor(private apiService: ApiService) {}

  getEnrollments(): Observable<Enrollment[]> {
    return this.apiService.getEnrollments();
  }

  createEnrollment(enrollment: Enrollment): Observable<any> {
    return this.apiService.createEnrollment(enrollment);
  }
}
