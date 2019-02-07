import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITypeMembreJury } from 'app/shared/model/type-membre-jury.model';

type EntityResponseType = HttpResponse<ITypeMembreJury>;
type EntityArrayResponseType = HttpResponse<ITypeMembreJury[]>;

@Injectable({ providedIn: 'root' })
export class TypeMembreJuryService {
    public resourceUrl = SERVER_API_URL + 'api/type-membre-juries';

    constructor(protected http: HttpClient) {}

    create(typeMembreJury: ITypeMembreJury): Observable<EntityResponseType> {
        return this.http.post<ITypeMembreJury>(this.resourceUrl, typeMembreJury, { observe: 'response' });
    }

    update(typeMembreJury: ITypeMembreJury): Observable<EntityResponseType> {
        return this.http.put<ITypeMembreJury>(this.resourceUrl, typeMembreJury, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITypeMembreJury>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITypeMembreJury[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
