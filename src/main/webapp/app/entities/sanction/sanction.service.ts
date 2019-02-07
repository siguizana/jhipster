import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISanction } from 'app/shared/model/sanction.model';

type EntityResponseType = HttpResponse<ISanction>;
type EntityArrayResponseType = HttpResponse<ISanction[]>;

@Injectable({ providedIn: 'root' })
export class SanctionService {
    public resourceUrl = SERVER_API_URL + 'api/sanctions';

    constructor(protected http: HttpClient) {}

    create(sanction: ISanction): Observable<EntityResponseType> {
        return this.http.post<ISanction>(this.resourceUrl, sanction, { observe: 'response' });
    }

    update(sanction: ISanction): Observable<EntityResponseType> {
        return this.http.put<ISanction>(this.resourceUrl, sanction, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISanction>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISanction[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
