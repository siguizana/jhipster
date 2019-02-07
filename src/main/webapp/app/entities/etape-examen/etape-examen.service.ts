import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEtapeExamen } from 'app/shared/model/etape-examen.model';

type EntityResponseType = HttpResponse<IEtapeExamen>;
type EntityArrayResponseType = HttpResponse<IEtapeExamen[]>;

@Injectable({ providedIn: 'root' })
export class EtapeExamenService {
    public resourceUrl = SERVER_API_URL + 'api/etape-examen';

    constructor(protected http: HttpClient) {}

    create(etapeExamen: IEtapeExamen): Observable<EntityResponseType> {
        return this.http.post<IEtapeExamen>(this.resourceUrl, etapeExamen, { observe: 'response' });
    }

    update(etapeExamen: IEtapeExamen): Observable<EntityResponseType> {
        return this.http.put<IEtapeExamen>(this.resourceUrl, etapeExamen, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEtapeExamen>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEtapeExamen[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
