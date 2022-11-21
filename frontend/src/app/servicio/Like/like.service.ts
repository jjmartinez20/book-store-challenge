import { Observable } from 'rxjs';
import { LikePeticion } from './../../modelo/like-peticion';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LikeRespuesta } from 'src/app/modelo/like-respuesta';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LikeService {

  private url: string = `${environment.apiUrl}/likes`;

  constructor(private http: HttpClient) { }

  nuevoLike(like: LikePeticion): Observable<LikeRespuesta> {
    return this.http.post<LikeRespuesta>(this.url, like);
  }

}
