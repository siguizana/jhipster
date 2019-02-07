import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IChoixEtablissement } from 'app/shared/model/choix-etablissement.model';

type EntityResponseType = HttpResponse<IChoixEtablissement>;
type EntityArrayResponseType = HttpResponse<IChoixEtablissement[]>;

@Injectable({ providedIn: 'root' })
export class ChoixEtablissementService {
    public resourceUrl = SERVER_API_URL + 'api/choix-etablissements';

    constructor(protected http: HttpClient) {}

    create(choixEtablissement: IChoixEtablissement): Observable<EntityResponseType> {
        return this.http.post<IChoixEtablissement>(this.resourceUrl, choixEtablissement, { observe: 'response' });
    }

    update(choixEtablissement: IChoixEtablissement): Observable<EntityResponseType> {
        return this.http.put<IChoixEtablissement>(this.resourceUrl, choixEtablissement, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IChoixEtablissement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IChoixEtablissement[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
