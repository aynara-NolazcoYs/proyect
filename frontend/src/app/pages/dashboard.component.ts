import { Component, OnInit } from '@angular/core';
import { DashboardData } from '@app/shared/models';
import { ApiService } from '@app/core/services';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  dashboardData: DashboardData = {
    totalStudents: 0,
    totalEnrollments: 0,
    totalPromoters: 0
  };
  loading = false;
  error: string | null = null;

  // Panel visibility
  showStudentsPanel = false;
  showPromotersPanel = false;
  showEnrollmentPanel = false;

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.loadDashboard();
  }

  loadDashboard(): void {
    this.loading = true;
    this.error = null;
    this.apiService.getDashboard().subscribe({
      next: (data) => {
        this.dashboardData = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Error al cargar el dashboard';
        this.loading = false;
        console.error(err);
      }
    });
  }

  openStudentsPanel(): void {
    this.closeAllPanels();
    this.showStudentsPanel = true;
  }

  openPromotersPanel(): void {
    this.closeAllPanels();
    this.showPromotersPanel = true;
  }

  openEnrollmentPanel(): void {
    this.closeAllPanels();
    this.showEnrollmentPanel = true;
  }

  closeAllPanels(): void {
    this.showStudentsPanel = false;
    this.showPromotersPanel = false;
    this.showEnrollmentPanel = false;
  }
}
