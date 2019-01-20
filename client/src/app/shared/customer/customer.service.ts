import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  public API = '//localhost:8080/customers';

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<any> {
    return this.http.get(this.API);
  }

  get(id: string) {
    return this.http.get(`${this.API}/${id}`);
  }

  save(customer: any): Observable<any> {
    let result: Observable<Object>;

    if (customer.id) {
      result = this.http.put(`${this.API}/${customer.id}`, customer);
    } else {
      result = this.http.post(this.API, customer);
    }
    return result;
  }

  remove(id: string) {
    return this.http.delete(`${this.API}/${id}`);
  }

}
