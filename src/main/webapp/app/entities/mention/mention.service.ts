import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMention } from 'app/shared/model/mention.model';

type EntityResponseType = HttpResponse<IMention>;
type EntityArrayResponseType = HttpResponse<IMention[]>;

@Injectable({ providedIn: 'root' })
export class MentionService {
    public resourceUrl = SERVER_API_URL + 'api/mentions';

    constructor(protected http: HttpClient) {}

    create(mention: IMention): Observable<EntityResponseType> {
        return this.http.post<IMention>(this.resourceUrl, mention, { observe: 'response' });
    }

    update(mention: IMention): Observable<EntityResponseType> {
        return this.http.put<IMention>(this.resourceUrl, mention, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMention>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMention[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
