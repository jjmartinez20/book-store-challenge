import { Transaction } from './../../modelo/transaction';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  private url: string = `${environment.apiUrl}/transactions/books`;

  constructor(private http: HttpClient) { }

  buscarTransacciones(bookId: number, from: Date, to: Date): Observable<Transaction> {
    let params: any = {};
    if (from) params.from = from;
    if (to) params.to = to;
    return this.http.get<Transaction>(`${this.url}/${bookId}`, {
      params
    });
  }

}
