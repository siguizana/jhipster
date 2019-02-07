import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEpreuveSpecialiteOption } from 'app/shared/model/epreuve-specialite-option.model';

type EntityResponseType = HttpResponse<IEpreuveSpecialiteOption>;
type EntityArrayResponseType = HttpResponse<IEpreuveSpecialiteOption[]>;

@Injectable({ providedIn: 'root' })
export class EpreuveSpecialiteOptionService {
    public resourceUrl = SERVER_API_URL + 'api/epreuve-specialite-options';

    constructor(protected http: HttpClient) {}

    create(epreuveSpecialiteOption: IEpreuveSpecialiteOption): Observable<EntityResponseType> {
        return this.http.post<IEpreuveSpecialiteOption>(this.resourceUrl, epreuveSpecialiteOption, { observe: 'response' });
    }

    update(epreuveSpecialiteOption: IEpreuveSpecialiteOption): Observable<EntityResponseType> {
        return this.http.put<IEpreuveSpecialiteOption>(this.resourceUrl, epreuveSpecialiteOption, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEpreuveSpecialiteOption>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEpreuveSpecialiteOption[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
