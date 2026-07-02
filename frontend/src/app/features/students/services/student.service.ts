import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Student } from '@app/shared/models';
import { ApiService } from '@app/core/services';

@Injectable({
  providedIn: 'root'
})
export class StudentService {
  constructor(private apiService: ApiService) {}

  getStudents(): Observable<Student[]> {
    return this.apiService.getStudents();
  }
}
