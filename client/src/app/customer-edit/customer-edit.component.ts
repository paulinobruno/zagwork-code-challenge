import { Component, OnDestroy, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { CustomerService } from '../shared/customer/customer.service';

@Component({
  selector: 'app-customer-edit',
  templateUrl: './customer-edit.component.html',
  styleUrls: ['./customer-edit.component.css']
})
export class CustomerEditComponent implements OnInit, OnDestroy {
  customer: any = {};
  sub: Subscription;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private customerService: CustomerService) {
  }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      const { id } = params;

      if (id) {
        this.customerService.get(id).subscribe((customer: any) => {
          if (customer) {
            this.customer = customer;
          } else {
            console.log(`Customer with id '${id}' not found, returning to list`);
            this.gotoList();
          }
        });
      }
    });
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

  gotoList() {
    this.router.navigate(['/customer-list']);
  }

  save(form: NgForm) {
    this.customerService.save(form).subscribe(_ => {
      this.gotoList();
    }, error => console.error(error));
  }

  remove(id) {
    this.customerService.remove(id).subscribe(result => {
      this.gotoList();
    }, error => console.error(error));
  }

}
