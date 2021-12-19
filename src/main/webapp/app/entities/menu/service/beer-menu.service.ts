import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { BeerMenu } from 'app/entities/menu/model/menu.model';
import { Observable } from 'rxjs';
import { IBeer } from './../../beer/beer.model';


export type EntityResponseType = HttpResponse<IBeer>;
export type EntityArrayResponseType = HttpResponse<IBeer[]>;

@Injectable({ providedIn: 'root' })
export class BeerMenuService {
    protected resourceUrl = this.applicationConfigService.getEndpointFor('api/beers');

    constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) { }

    saveMenu(dataRequests: BeerMenu): Observable<EntityResponseType> {
        return this.http.post<IBeer>("http://localhost:8080/api/orders/save-data", dataRequests, { observe: 'response' });
    }

    getALlBeer(url: string): Observable<any> {
        return this.http.get<IBeer[]>("http://localhost:8080/api/beers" + url);
    }
}
