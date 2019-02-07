import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITypeCritere } from 'app/shared/model/type-critere.model';

type EntityResponseType = HttpResponse<ITypeCritere>;
type EntityArrayResponseType = HttpResponse<ITypeCritere[]>;

@Injectable({ providedIn: 'root' })
export class TypeCritereService {
    public resourceUrl = SERVER_API_URL + 'api/type-criteres';

    constructor(protected http: HttpClient) {}

    create(typeCritere: ITypeCritere): Observable<EntityResponseType> {
        return this.http.post<ITypeCritere>(this.resourceUrl, typeCritere, { observe: 'response' });
    }

    update(typeCritere: ITypeCritere): Observable<EntityResponseType> {
        return this.http.put<ITypeCritere>(this.resourceUrl, typeCritere, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITypeCritere>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITypeCritere[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
