import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICentreCompositioJury } from 'app/shared/model/centre-compositio-jury.model';

type EntityResponseType = HttpResponse<ICentreCompositioJury>;
type EntityArrayResponseType = HttpResponse<ICentreCompositioJury[]>;

@Injectable({ providedIn: 'root' })
export class CentreCompositioJuryService {
    public resourceUrl = SERVER_API_URL + 'api/centre-compositio-juries';

    constructor(protected http: HttpClient) {}

    create(centreCompositioJury: ICentreCompositioJury): Observable<EntityResponseType> {
        return this.http.post<ICentreCompositioJury>(this.resourceUrl, centreCompositioJury, { observe: 'response' });
    }

    update(centreCompositioJury: ICentreCompositioJury): Observable<EntityResponseType> {
        return this.http.put<ICentreCompositioJury>(this.resourceUrl, centreCompositioJury, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICentreCompositioJury>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICentreCompositioJury[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
