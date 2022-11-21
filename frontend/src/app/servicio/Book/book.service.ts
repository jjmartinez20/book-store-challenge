import { BookPagina } from './../../modelo/book-pagina';
import { Book } from './../../modelo/book';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class BookService {

  private url: string = `${environment.apiUrl}/books`;

  constructor(private http: HttpClient) {}

  obtenerBooks(
    size: number,
    page: number,
    unavailable: boolean,
    title: string
  ): Observable<BookPagina | Book[]> {
    title = title.trim();
    let params: any = {};
    if (size > 0) params.size = size;
    if (page > 0) params.page = page;
    if (title !== '') params.title = title;
    params.unavailable = unavailable;
    return this.http.get<BookPagina | Book[]>(this.url, {
      params,
    });
  }

  nuevoBook(book: Book): Observable<Book> {
    return this.http.post<Book>(this.url, book);
  }

  actualizarBook(book: Book, id: number): Observable<Book> {
    return this.http.put<Book>(`${this.url}/${id}`, book);
  }

  parcharBook(datos: any, id: number): Observable<Book> {
    return this.http.patch<Book>(`${this.url}/${id}`, datos);
  }

  eliminarBook(id: number): Observable<unknown> {
    return this.http.delete(`${this.url}/${id}`);
  }
}
