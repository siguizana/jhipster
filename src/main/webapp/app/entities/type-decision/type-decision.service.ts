import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITypeDecision } from 'app/shared/model/type-decision.model';

type EntityResponseType = HttpResponse<ITypeDecision>;
type EntityArrayResponseType = HttpResponse<ITypeDecision[]>;

@Injectable({ providedIn: 'root' })
export class TypeDecisionService {
    public resourceUrl = SERVER_API_URL + 'api/type-decisions';

    constructor(protected http: HttpClient) {}

    create(typeDecision: ITypeDecision): Observable<EntityResponseType> {
        return this.http.post<ITypeDecision>(this.resourceUrl, typeDecision, { observe: 'response' });
    }

    update(typeDecision: ITypeDecision): Observable<EntityResponseType> {
        return this.http.put<ITypeDecision>(this.resourceUrl, typeDecision, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITypeDecision>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITypeDecision[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
