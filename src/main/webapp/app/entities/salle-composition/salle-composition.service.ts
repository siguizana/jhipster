import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISalleComposition } from 'app/shared/model/salle-composition.model';

type EntityResponseType = HttpResponse<ISalleComposition>;
type EntityArrayResponseType = HttpResponse<ISalleComposition[]>;

@Injectable({ providedIn: 'root' })
export class SalleCompositionService {
    public resourceUrl = SERVER_API_URL + 'api/salle-compositions';

    constructor(protected http: HttpClient) {}

    create(salleComposition: ISalleComposition): Observable<EntityResponseType> {
        return this.http.post<ISalleComposition>(this.resourceUrl, salleComposition, { observe: 'response' });
    }

    update(salleComposition: ISalleComposition): Observable<EntityResponseType> {
        return this.http.put<ISalleComposition>(this.resourceUrl, salleComposition, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISalleComposition>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISalleComposition[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
