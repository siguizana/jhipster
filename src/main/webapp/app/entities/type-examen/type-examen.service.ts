import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITypeExamen } from 'app/shared/model/type-examen.model';

type EntityResponseType = HttpResponse<ITypeExamen>;
type EntityArrayResponseType = HttpResponse<ITypeExamen[]>;

@Injectable({ providedIn: 'root' })
export class TypeExamenService {
    public resourceUrl = SERVER_API_URL + 'api/type-examen';

    constructor(protected http: HttpClient) {}

    create(typeExamen: ITypeExamen): Observable<EntityResponseType> {
        return this.http.post<ITypeExamen>(this.resourceUrl, typeExamen, { observe: 'response' });
    }

    update(typeExamen: ITypeExamen): Observable<EntityResponseType> {
        return this.http.put<ITypeExamen>(this.resourceUrl, typeExamen, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITypeExamen>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITypeExamen[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
