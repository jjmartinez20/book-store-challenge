import { Observable } from 'rxjs';
import { SalePeticion } from './../../modelo/sale-peticion';
import { SaleRespuesta } from './../../modelo/sale-respuesta';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SaleService {

  private url: string = `${environment.apiUrl}/sales`;

  constructor(private http: HttpClient) { }

  nuevaSale(sale: SalePeticion): Observable<SaleRespuesta> {
    return this.http.post<SaleRespuesta>(this.url, sale);
  }
}
