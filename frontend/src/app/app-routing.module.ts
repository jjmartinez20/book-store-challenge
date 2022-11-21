import { TransactionsComponent } from './componente/transactions/transactions.component';
import { BooksComponent } from './componente/books/books.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {path: 'books', component: BooksComponent},
  {path: '', component: BooksComponent},
  {path: 'transactions', component: TransactionsComponent},
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
