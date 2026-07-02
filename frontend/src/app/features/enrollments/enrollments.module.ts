import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { EnrollmentFormComponent } from './components/enrollment-form.component';

@NgModule({
  declarations: [
    EnrollmentFormComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule
  ],
  exports: [
    EnrollmentFormComponent
  ]
})
export class EnrollmentsModule { }
