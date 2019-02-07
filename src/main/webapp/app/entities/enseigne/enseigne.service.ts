import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEnseigne } from 'app/shared/model/enseigne.model';

type EntityResponseType = HttpResponse<IEnseigne>;
type EntityArrayResponseType = HttpResponse<IEnseigne[]>;

@Injectable({ providedIn: 'root' })
export class EnseigneService {
    public resourceUrl = SERVER_API_URL + 'api/enseignes';

    constructor(protected http: HttpClient) {}

    create(enseigne: IEnseigne): Observable<EntityResponseType> {
        return this.http.post<IEnseigne>(this.resourceUrl, enseigne, { observe: 'response' });
    }

    update(enseigne: IEnseigne): Observable<EntityResponseType> {
        return this.http.put<IEnseigne>(this.resourceUrl, enseigne, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEnseigne>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEnseigne[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
