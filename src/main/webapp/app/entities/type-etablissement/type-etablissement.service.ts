import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITypeEtablissement } from 'app/shared/model/type-etablissement.model';

type EntityResponseType = HttpResponse<ITypeEtablissement>;
type EntityArrayResponseType = HttpResponse<ITypeEtablissement[]>;

@Injectable({ providedIn: 'root' })
export class TypeEtablissementService {
    public resourceUrl = SERVER_API_URL + 'api/type-etablissements';

    constructor(protected http: HttpClient) {}

    create(typeEtablissement: ITypeEtablissement): Observable<EntityResponseType> {
        return this.http.post<ITypeEtablissement>(this.resourceUrl, typeEtablissement, { observe: 'response' });
    }

    update(typeEtablissement: ITypeEtablissement): Observable<EntityResponseType> {
        return this.http.put<ITypeEtablissement>(this.resourceUrl, typeEtablissement, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITypeEtablissement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITypeEtablissement[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
