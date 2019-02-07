import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMembreJuryJury } from 'app/shared/model/membre-jury-jury.model';

type EntityResponseType = HttpResponse<IMembreJuryJury>;
type EntityArrayResponseType = HttpResponse<IMembreJuryJury[]>;

@Injectable({ providedIn: 'root' })
export class MembreJuryJuryService {
    public resourceUrl = SERVER_API_URL + 'api/membre-jury-juries';

    constructor(protected http: HttpClient) {}

    create(membreJuryJury: IMembreJuryJury): Observable<EntityResponseType> {
        return this.http.post<IMembreJuryJury>(this.resourceUrl, membreJuryJury, { observe: 'response' });
    }

    update(membreJuryJury: IMembreJuryJury): Observable<EntityResponseType> {
        return this.http.put<IMembreJuryJury>(this.resourceUrl, membreJuryJury, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMembreJuryJury>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMembreJuryJury[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
