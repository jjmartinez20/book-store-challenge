import swal  from 'sweetalert2';
import { TransactionService } from './../../servicio/Transaction/transaction.service';
import { BookService } from './../../servicio/Book/book.service';
import { FormControl } from '@angular/forms';
import { Book } from './../../modelo/book';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-transactions',
  templateUrl: './transactions.component.html',
  styleUrls: ['./transactions.component.css']
})
export class TransactionsComponent implements OnInit {

  libros: Book[] = [];
  sales: Date[] = [];
  customers: string[] = [];
  libro: FormControl = new FormControl(null);
  from: FormControl = new FormControl(null);
  to: FormControl = new FormControl(null);
  totalRevenue: number = 0;

  constructor(
    private bookServicio: BookService,
    private transactionServicio: TransactionService
  ) {}

  ngOnInit(): void {
    this.bookServicio.obtenerBooks(0, 0, false, '').subscribe({
      next: respuesta => {
        if (Array.isArray(respuesta)) {
          this.libros = respuesta;
        }
      },
      error: err => {
        console.error(err);
      }
    })
  }

  buscarTransacciones(): void {
    if (this.libro.value) {
      this.transactionServicio.buscarTransacciones(this.libro.value, this.from.value, this.to.value).subscribe({
        next: respuesta => {
          this.customers = respuesta.customers;
          this.sales = respuesta.sales;
          this.totalRevenue = respuesta.totalRevenue;
        },
        error: err => {
          this.sales = [];
          this.customers = [];
          this.totalRevenue = 0;
          if (err.status === 404) {
            swal.fire({
              text: 'No se ha registrado ninguna venta que cumpla los parámetros seleccionados',
              icon: 'warning'
            })
          }
          console.error(err);
        }
      })
    }
  }

}
