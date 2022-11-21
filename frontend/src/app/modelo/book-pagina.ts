import { Book } from './book';
export interface BookPagina {
  content: Book[];
  size: number;
  numberOfElements: number;
  totalElements: number;
  totalPages: number;
  number: number;
}
