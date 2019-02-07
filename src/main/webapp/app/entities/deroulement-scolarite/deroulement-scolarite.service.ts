import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDeroulementScolarite } from 'app/shared/model/deroulement-scolarite.model';

type EntityResponseType = HttpResponse<IDeroulementScolarite>;
type EntityArrayResponseType = HttpResponse<IDeroulementScolarite[]>;

@Injectable({ providedIn: 'root' })
export class DeroulementScolariteService {
    public resourceUrl = SERVER_API_URL + 'api/deroulement-scolarites';

    constructor(protected http: HttpClient) {}

    create(deroulementScolarite: IDeroulementScolarite): Observable<EntityResponseType> {
        return this.http.post<IDeroulementScolarite>(this.resourceUrl, deroulementScolarite, { observe: 'response' });
    }

    update(deroulementScolarite: IDeroulementScolarite): Observable<EntityResponseType> {
        return this.http.put<IDeroulementScolarite>(this.resourceUrl, deroulementScolarite, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDeroulementScolarite>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDeroulementScolarite[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
