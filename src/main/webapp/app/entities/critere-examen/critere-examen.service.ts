import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICritereExamen } from 'app/shared/model/critere-examen.model';

type EntityResponseType = HttpResponse<ICritereExamen>;
type EntityArrayResponseType = HttpResponse<ICritereExamen[]>;

@Injectable({ providedIn: 'root' })
export class CritereExamenService {
    public resourceUrl = SERVER_API_URL + 'api/critere-examen';

    constructor(protected http: HttpClient) {}

    create(critereExamen: ICritereExamen): Observable<EntityResponseType> {
        return this.http.post<ICritereExamen>(this.resourceUrl, critereExamen, { observe: 'response' });
    }

    update(critereExamen: ICritereExamen): Observable<EntityResponseType> {
        return this.http.put<ICritereExamen>(this.resourceUrl, critereExamen, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICritereExamen>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICritereExamen[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
