import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGroupeEpreuve } from 'app/shared/model/groupe-epreuve.model';

type EntityResponseType = HttpResponse<IGroupeEpreuve>;
type EntityArrayResponseType = HttpResponse<IGroupeEpreuve[]>;

@Injectable({ providedIn: 'root' })
export class GroupeEpreuveService {
    public resourceUrl = SERVER_API_URL + 'api/groupe-epreuves';

    constructor(protected http: HttpClient) {}

    create(groupeEpreuve: IGroupeEpreuve): Observable<EntityResponseType> {
        return this.http.post<IGroupeEpreuve>(this.resourceUrl, groupeEpreuve, { observe: 'response' });
    }

    update(groupeEpreuve: IGroupeEpreuve): Observable<EntityResponseType> {
        return this.http.put<IGroupeEpreuve>(this.resourceUrl, groupeEpreuve, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IGroupeEpreuve>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGroupeEpreuve[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
