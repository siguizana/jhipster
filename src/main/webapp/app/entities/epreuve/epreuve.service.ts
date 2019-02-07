import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEpreuve } from 'app/shared/model/epreuve.model';

type EntityResponseType = HttpResponse<IEpreuve>;
type EntityArrayResponseType = HttpResponse<IEpreuve[]>;

@Injectable({ providedIn: 'root' })
export class EpreuveService {
    public resourceUrl = SERVER_API_URL + 'api/epreuves';

    constructor(protected http: HttpClient) {}

    create(epreuve: IEpreuve): Observable<EntityResponseType> {
        return this.http.post<IEpreuve>(this.resourceUrl, epreuve, { observe: 'response' });
    }

    update(epreuve: IEpreuve): Observable<EntityResponseType> {
        return this.http.put<IEpreuve>(this.resourceUrl, epreuve, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEpreuve>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEpreuve[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
