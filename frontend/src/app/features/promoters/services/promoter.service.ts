import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Promoter } from '@app/shared/models';
import { ApiService } from '@app/core/services';

@Injectable({
  providedIn: 'root'
})
export class PromoterService {
  constructor(private apiService: ApiService) {}

  getPromoters(): Observable<Promoter[]> {
    return this.apiService.getPromoters();
  }
}
