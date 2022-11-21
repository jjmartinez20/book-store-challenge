import { SalePeticion } from './../../modelo/sale-peticion';
import { SaleService } from './../../servicio/Sale/sale.service';
import { LikeService } from './../../servicio/Like/like.service';
import { Book } from './../../modelo/book';
import { BookService } from './../../servicio/Book/book.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { LikePeticion } from 'src/app/modelo/like-peticion';
import * as bootstrap from 'bootstrap';
import swal from 'sweetalert2';

@Component({
  selector: 'app-books',
  templateUrl: './books.component.html',
  styleUrls: ['./books.component.css'],
})
export class BooksComponent implements OnInit {
  data: Book[] = [];
  p: number = 1;
  total: number = 0;
  cargando: boolean = false;
  timeout: any;
  mostrarTodos: FormControl = new FormControl(false);
  tamanioPagina: FormControl = new FormControl(12);
  title: FormControl = new FormControl('');
  config: any = {
    id: 'server',
    itemsPerPage: this.tamanioPagina.value,
    currentPage: this.p,
    totalItems: this.total,
  };

  private elementoLike?: HTMLElement;
  private likeModal: any;
  private saleModal: any;
  private bookModal: any;

  likeForm = new FormGroup({
    bookId: new FormControl(),
    customerEmail: new FormControl('', [Validators.required, Validators.email]),
  });

  saleForm = new FormGroup({
    bookId: new FormControl(),
    customerEmail: new FormControl('', [Validators.required, Validators.email]),
  });

  bookForm = new FormGroup({
    bookId: new FormControl(0),
    title: new FormControl('', [Validators.required]),
    description: new FormControl(''),
    stock: new FormControl(0, [Validators.required, Validators.min(0)]),
    salePrice: new FormControl(0, [Validators.required, Validators.min(0.01)]),
    available: new FormControl(true),
  });

  constructor(
    private bookServicio: BookService,
    private likeServicio: LikeService,
    private saleServicio: SaleService
  ) {}

  ngOnInit(): void {
    this.getPage(1);
    this.likeModal = new bootstrap.Modal(
      document.getElementById('modalLike') || ''
    );
    this.saleModal = new bootstrap.Modal(
      document.getElementById('modalSale') || ''
    );
    this.bookModal = new bootstrap.Modal(
      document.getElementById('modalBook') || ''
    );
  }

  getPage(page: number): void {
    this.cargando = true;
    this.bookServicio
      .obtenerBooks(this.tamanioPagina.value, page, this.mostrarTodos.value, this.title.value.trim())
      .subscribe({
        next: (respuesta) => {
          if (!Array.isArray(respuesta)) {
            this.p = page;
            this.data = respuesta.content;
            this.total = respuesta.totalElements;
            this.cargando = false;
            this.config.totalItems = this.total;
            this.config.itemsPerPage = this.tamanioPagina.value;
            this.config.currentPage = this.p;
          }
        },
        error: (err) => console.error(err),
      });
  }

  esperarDejarEscribir(): void {
    if (this.timeout) {
      clearTimeout(this.timeout);
    }
    this.timeout = setTimeout(() => {
      this.getPage(1);
    }, 300);
  }

  iconoSinRelleno(elemento: HTMLElement): void {
    elemento.classList.value = '';
    elemento.classList.add('fa-regular', 'fa-heart', 'fa-lg');
  }

  iconoConRelleno(elemento: HTMLElement): void {
    elemento.classList.value = '';
    elemento.classList.add('fa-solid', 'fa-heart', 'fa-lg');
  }

  abrirModalLike(book: Book, elemento: HTMLElement): void {
    if (!book.available) {
      swal.fire({
        title: 'Advertencia',
        text: `El libro '${book.title}' no está disponible`,
        icon: 'warning',
      });
      return;
    }
    this.elementoLike = elemento;
    this.likeForm.patchValue({
      bookId: book.bookId,
      customerEmail: '',
    });
    this.likeForm.markAsUntouched();
    this.likeModal.show();
  }

  abrirModalVenta(book: Book): void {
    if (!book.available) {
      swal.fire({
        title: 'Advertencia',
        text: `El libro '${book.title}' no está disponible`,
        icon: 'warning',
      });
      return;
    }
    if (book.stock === 0) {
      swal.fire({
        title: 'Advertencia',
        text: `El libro '${book.title}' no tiene stock disponible para la venta`,
        icon: 'warning',
      });
      return;
    }
    this.saleForm.patchValue({
      bookId: book.bookId,
      customerEmail: '',
    });
    this.saleForm.markAsUntouched();
    this.saleModal.show();
  }

  abrirModalBook(book?: Book): void {
    if (book) {
      this.bookForm.patchValue(book);
    } else {
      this.bookForm.patchValue({
        bookId: 0,
        title: '',
        description: '',
        stock: 0,
        salePrice: 0,
        available: true,
      });
    }
    this.bookForm.markAsUntouched();
    this.bookModal.show();
  }

  guardarLike(): void {
    this.likeServicio.nuevoLike(this.likeForm.value as LikePeticion).subscribe({
      next: (respuesta) => {
        this.likeModal.hide();
        if (this.elementoLike) {
          this.elementoLike.innerText += ' ' + respuesta.likes;
          setTimeout(() => {
            if (this.elementoLike) this.elementoLike.innerText = '';
          }, 1500);
        }
      },
      error: (err) => {
        this.likeModal.hide();
        swal.fire({
          title: 'Error',
          text: err.error.mensaje,
          icon: 'error',
        });
        console.error(err);
      },
    });
  }

  guardarSale(): void {
    this.saleServicio.nuevaSale(this.saleForm.value as SalePeticion).subscribe({
      next: (respuesta) => {
        this.saleModal.hide();
        this.getPage(this.p);
      },
      error: (err) => {
        this.saleModal.hide();
        swal.fire({
          title: 'Error',
          text: err.error.mensaje,
          icon: 'error',
        });
        console.error(err);
      },
    });
  }

  guardarBook(): void {
    const book: Book = this.bookForm.value as Book;
    if (book.bookId === 0) {
      this.bookServicio.nuevoBook(book).subscribe({
        next: (respuesta) => {
          this.bookModal.hide();
          this.getPage(this.p);
        },
        error: (err) => {
          this.bookModal.hide();
          swal.fire({
            title: 'Error',
            text: err.error.mensaje,
            icon: 'error',
          });
          console.error(err);
        },
      });
    } else {
      this.bookServicio.actualizarBook(book, book.bookId).subscribe({
        next: (respuesta) => {
          this.bookModal.hide();
          this.getPage(this.p);
        },
        error: (err) => {
          this.bookModal.hide();
          swal.fire({
            title: 'Error',
            text: err.error.mensaje,
            icon: 'error',
          });
          console.error(err);
        },
      });
    }
  }

  eliminarBook(book: Book) {
    swal
      .fire({
        title: 'Eliminar libro',
        text: `¿Está seguro(a) de eliminar el libro '${book.title}'?`,
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        confirmButtonText: 'Eliminar',
        cancelButtonText: 'Cancelar',
      })
      .then((result) => {
        if (result.isConfirmed) {
          this.bookServicio.eliminarBook(book.bookId).subscribe({
            next: (respuesta) => {
              swal.fire({
                title: 'Eliminado',
                text: `El libro '${book.title}' ha sido eliminado correctamente`,
                icon: 'success',
              });
              this.getPage(this.p);
            },
            error: (err) => {
              swal.fire({
                title: 'Error',
                text: err.error.mensaje,
                icon: 'error',
              });
              console.error(err);
            },
          });
        }
      });
  }
}
