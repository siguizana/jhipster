import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHandicape } from 'app/shared/model/handicape.model';

type EntityResponseType = HttpResponse<IHandicape>;
type EntityArrayResponseType = HttpResponse<IHandicape[]>;

@Injectable({ providedIn: 'root' })
export class HandicapeService {
    public resourceUrl = SERVER_API_URL + 'api/handicapes';

    constructor(protected http: HttpClient) {}

    create(handicape: IHandicape): Observable<EntityResponseType> {
        return this.http.post<IHandicape>(this.resourceUrl, handicape, { observe: 'response' });
    }

    update(handicape: IHandicape): Observable<EntityResponseType> {
        return this.http.put<IHandicape>(this.resourceUrl, handicape, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IHandicape>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IHandicape[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
