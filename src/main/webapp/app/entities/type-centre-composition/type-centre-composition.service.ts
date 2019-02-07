import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITypeCentreComposition } from 'app/shared/model/type-centre-composition.model';

type EntityResponseType = HttpResponse<ITypeCentreComposition>;
type EntityArrayResponseType = HttpResponse<ITypeCentreComposition[]>;

@Injectable({ providedIn: 'root' })
export class TypeCentreCompositionService {
    public resourceUrl = SERVER_API_URL + 'api/type-centre-compositions';

    constructor(protected http: HttpClient) {}

    create(typeCentreComposition: ITypeCentreComposition): Observable<EntityResponseType> {
        return this.http.post<ITypeCentreComposition>(this.resourceUrl, typeCentreComposition, { observe: 'response' });
    }

    update(typeCentreComposition: ITypeCentreComposition): Observable<EntityResponseType> {
        return this.http.put<ITypeCentreComposition>(this.resourceUrl, typeCentreComposition, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITypeCentreComposition>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITypeCentreComposition[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
