import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Promoter, Enrollment } from '@app/shared/models';
import { EnrollmentService } from '../services/enrollment.service';
import { PromoterService } from '../../promoters/services/promoter.service';

@Component({
  selector: 'app-enrollment-form',
  templateUrl: './enrollment-form.component.html',
  styleUrls: ['./enrollment-form.component.css']
})
export class EnrollmentFormComponent implements OnInit {
  enrollmentForm: FormGroup;
  promoters: Promoter[] = [];
  loading = false;
  submitted = false;
  successMessage: string | null = null;
  errorMessage: string | null = null;

  readonly SEDES = [
    { value: 'SED-001', label: 'SED-001' },
    { value: 'SED-002', label: 'SED-002' },
    { value: 'SED-003', label: 'SED-003' }
  ];

  readonly PROGRAMAS = [
    { value: 'Programa 1', label: 'Programa 1' },
    { value: 'Programa 2', label: 'Programa 2' }
  ];

  readonly MODALIDADES = [
    { value: 'Virtual', label: 'Virtual' },
    { value: 'Presencial', label: 'Presencial' }
  ];

  constructor(
    private fb: FormBuilder,
    private enrollmentService: EnrollmentService,
    private promoterService: PromoterService
  ) {
    this.enrollmentForm = this.fb.group({
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      dni: ['', Validators.required],
      sede: ['', Validators.required],
      promotor: ['', Validators.required],
      programa: ['', Validators.required],
      modalidad: ['', Validators.required],
      correo: ['', [Validators.required, Validators.email]]
    });
  }

  ngOnInit(): void {
    this.loadPromoters();
  }

  loadPromoters(): void {
    this.promoterService.getPromoters().subscribe({
      next: (data) => {
        this.promoters = data;
      },
      error: (err) => {
        console.error('Error cargando promotores', err);
      }
    });
  }

  onSubmit(): void {
    this.submitted = true;
    this.successMessage = null;
    this.errorMessage = null;

    if (this.enrollmentForm.invalid) {
      this.errorMessage = 'Por favor completa todos los campos correctamente';
      return;
    }

    this.loading = true;
    const enrollment: Enrollment = this.enrollmentForm.value;

    this.enrollmentService.createEnrollment(enrollment).subscribe({
      next: (response) => {
        this.successMessage = 'Matrícula registrada exitosamente';
        this.enrollmentForm.reset();
        this.submitted = false;
        this.loading = false;
      },
      error: (err) => {
        this.errorMessage = 'Error al registrar la matrícula';
        this.loading = false;
        console.error(err);
      }
    });
  }

  resetForm(): void {
    this.enrollmentForm.reset();
    this.submitted = false;
    this.successMessage = null;
    this.errorMessage = null;
  }
}
