import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IJury } from 'app/shared/model/jury.model';

type EntityResponseType = HttpResponse<IJury>;
type EntityArrayResponseType = HttpResponse<IJury[]>;

@Injectable({ providedIn: 'root' })
export class JuryService {
    public resourceUrl = SERVER_API_URL + 'api/juries';

    constructor(protected http: HttpClient) {}

    create(jury: IJury): Observable<EntityResponseType> {
        return this.http.post<IJury>(this.resourceUrl, jury, { observe: 'response' });
    }

    update(jury: IJury): Observable<EntityResponseType> {
        return this.http.put<IJury>(this.resourceUrl, jury, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IJury>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IJury[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
