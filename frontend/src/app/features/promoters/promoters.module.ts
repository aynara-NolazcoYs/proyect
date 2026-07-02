import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { PromotersListComponent } from './components/promoters-list.component';

@NgModule({
  declarations: [
    PromotersListComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule
  ],
  exports: [
    PromotersListComponent
  ]
})
export class PromotersModule { }
