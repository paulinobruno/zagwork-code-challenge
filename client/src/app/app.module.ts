import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { MatButtonModule, MatCardModule, MatInputModule, MatListModule, MatToolbarModule, MatSelectModule } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';

import { CustomerService } from './shared/customer/customer.service';
import { CustomerListComponent } from './customer-list/customer-list.component';
import { CustomerEditComponent } from './customer-edit/customer-edit.component';



const appRoutes: Routes = [
  { path: '', redirectTo: '/customer-list', pathMatch: 'full' },
  {
    path: 'customer-list',
    component: CustomerListComponent
  },
  {
    path: 'customer-add',
    component: CustomerEditComponent
  },
  {
    path: 'customer-edit/:id',
    component: CustomerEditComponent
  }
];

@NgModule({
  declarations: [
    AppComponent,
    CustomerListComponent,
    CustomerEditComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatCardModule,
    MatInputModule,
    MatListModule,
    MatToolbarModule,
    MatSelectModule,
    FormsModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [CustomerService],
  bootstrap: [AppComponent]
})
export class AppModule { }
