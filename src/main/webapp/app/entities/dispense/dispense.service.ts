import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDispense } from 'app/shared/model/dispense.model';

type EntityResponseType = HttpResponse<IDispense>;
type EntityArrayResponseType = HttpResponse<IDispense[]>;

@Injectable({ providedIn: 'root' })
export class DispenseService {
    public resourceUrl = SERVER_API_URL + 'api/dispenses';

    constructor(protected http: HttpClient) {}

    create(dispense: IDispense): Observable<EntityResponseType> {
        return this.http.post<IDispense>(this.resourceUrl, dispense, { observe: 'response' });
    }

    update(dispense: IDispense): Observable<EntityResponseType> {
        return this.http.put<IDispense>(this.resourceUrl, dispense, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDispense>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDispense[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
