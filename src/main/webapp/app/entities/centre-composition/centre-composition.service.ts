import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICentreComposition } from 'app/shared/model/centre-composition.model';

type EntityResponseType = HttpResponse<ICentreComposition>;
type EntityArrayResponseType = HttpResponse<ICentreComposition[]>;

@Injectable({ providedIn: 'root' })
export class CentreCompositionService {
    public resourceUrl = SERVER_API_URL + 'api/centre-compositions';

    constructor(protected http: HttpClient) {}

    create(centreComposition: ICentreComposition): Observable<EntityResponseType> {
        return this.http.post<ICentreComposition>(this.resourceUrl, centreComposition, { observe: 'response' });
    }

    update(centreComposition: ICentreComposition): Observable<EntityResponseType> {
        return this.http.put<ICentreComposition>(this.resourceUrl, centreComposition, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICentreComposition>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICentreComposition[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
