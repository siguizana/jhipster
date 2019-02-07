import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICentreExamen } from 'app/shared/model/centre-examen.model';

type EntityResponseType = HttpResponse<ICentreExamen>;
type EntityArrayResponseType = HttpResponse<ICentreExamen[]>;

@Injectable({ providedIn: 'root' })
export class CentreExamenService {
    public resourceUrl = SERVER_API_URL + 'api/centre-examen';

    constructor(protected http: HttpClient) {}

    create(centreExamen: ICentreExamen): Observable<EntityResponseType> {
        return this.http.post<ICentreExamen>(this.resourceUrl, centreExamen, { observe: 'response' });
    }

    update(centreExamen: ICentreExamen): Observable<EntityResponseType> {
        return this.http.put<ICentreExamen>(this.resourceUrl, centreExamen, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICentreExamen>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICentreExamen[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
