import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICeb } from 'app/shared/model/ceb.model';

type EntityResponseType = HttpResponse<ICeb>;
type EntityArrayResponseType = HttpResponse<ICeb[]>;

@Injectable({ providedIn: 'root' })
export class CebService {
    public resourceUrl = SERVER_API_URL + 'api/cebs';

    constructor(protected http: HttpClient) {}

    create(ceb: ICeb): Observable<EntityResponseType> {
        return this.http.post<ICeb>(this.resourceUrl, ceb, { observe: 'response' });
    }

    update(ceb: ICeb): Observable<EntityResponseType> {
        return this.http.put<ICeb>(this.resourceUrl, ceb, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICeb>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICeb[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
