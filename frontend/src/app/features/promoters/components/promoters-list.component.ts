import { Component, OnInit } from '@angular/core';
import { Promoter } from '@app/shared/models';
import { PromoterService } from '../services/promoter.service';

@Component({
  selector: 'app-promoters-list',
  templateUrl: './promoters-list.component.html',
  styleUrls: ['./promoters-list.component.css']
})
export class PromotersListComponent implements OnInit {
  promoters: Promoter[] = [];
  loading = false;
  error: string | null = null;

  constructor(private promoterService: PromoterService) {}

  ngOnInit(): void {
    this.loadPromoters();
  }

  loadPromoters(): void {
    this.loading = true;
    this.error = null;
    this.promoterService.getPromoters().subscribe({
      next: (data) => {
        this.promoters = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Error al cargar promotores';
        this.loading = false;
        console.error(err);
      }
    });
  }
}
