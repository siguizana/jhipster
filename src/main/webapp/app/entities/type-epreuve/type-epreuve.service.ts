import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITypeEpreuve } from 'app/shared/model/type-epreuve.model';

type EntityResponseType = HttpResponse<ITypeEpreuve>;
type EntityArrayResponseType = HttpResponse<ITypeEpreuve[]>;

@Injectable({ providedIn: 'root' })
export class TypeEpreuveService {
    public resourceUrl = SERVER_API_URL + 'api/type-epreuves';

    constructor(protected http: HttpClient) {}

    create(typeEpreuve: ITypeEpreuve): Observable<EntityResponseType> {
        return this.http.post<ITypeEpreuve>(this.resourceUrl, typeEpreuve, { observe: 'response' });
    }

    update(typeEpreuve: ITypeEpreuve): Observable<EntityResponseType> {
        return this.http.put<ITypeEpreuve>(this.resourceUrl, typeEpreuve, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITypeEpreuve>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITypeEpreuve[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
