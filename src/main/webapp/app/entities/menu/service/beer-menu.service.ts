import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { BeerMenu } from 'app/entities/menu/model/menu.model';
import { Observable } from 'rxjs';
import { IBeer } from './../../beer/beer.model';
import { CartClient } from './../model/cart-user.model';

export type EntityResponseType = HttpResponse<CartClient>;
export type EntityArrayResponseType = HttpResponse<IBeer[]>;

@Injectable({ providedIn: 'root' })
export class BeerMenuService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/beers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  saveMenu(dataRequests: BeerMenu): Observable<EntityResponseType> {
    return this.http.post<IBeer>('http://localhost:8080/api/orders/save-data', dataRequests, { observe: 'response' });
  }

  getALlBeer(url: string): Observable<any> {
    return this.http.get<IBeer[]>('http://localhost:8080/api/beers' + url);
  }

  getCartClient(url: string): Observable<any> {
    return this.http.get<IBeer[]>('http://localhost:8080/api/beers' + url);
  }

  getClient(param: any): Observable<any> {
    return this.http.get<IBeer[]>('http://localhost:8080/api/orders/get-client/' + param);
  }

  deleteClient(id: number): Observable<any> {
    return this.http.delete('http://localhost:8080/api/delete-beer-menu/' + id, { observe: 'response' });
  }
}
