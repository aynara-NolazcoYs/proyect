import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardComponent } from './pages/dashboard.component';

import { StudentsModule } from './features/students/students.module';
import { PromotersModule } from './features/promoters/promoters.module';
import { EnrollmentsModule } from './features/enrollments/enrollments.module';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    StudentsModule,
    PromotersModule,
    EnrollmentsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
