import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICritereChoixMembreJury } from 'app/shared/model/critere-choix-membre-jury.model';

type EntityResponseType = HttpResponse<ICritereChoixMembreJury>;
type EntityArrayResponseType = HttpResponse<ICritereChoixMembreJury[]>;

@Injectable({ providedIn: 'root' })
export class CritereChoixMembreJuryService {
    public resourceUrl = SERVER_API_URL + 'api/critere-choix-membre-juries';

    constructor(protected http: HttpClient) {}

    create(critereChoixMembreJury: ICritereChoixMembreJury): Observable<EntityResponseType> {
        return this.http.post<ICritereChoixMembreJury>(this.resourceUrl, critereChoixMembreJury, { observe: 'response' });
    }

    update(critereChoixMembreJury: ICritereChoixMembreJury): Observable<EntityResponseType> {
        return this.http.put<ICritereChoixMembreJury>(this.resourceUrl, critereChoixMembreJury, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICritereChoixMembreJury>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICritereChoixMembreJury[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
