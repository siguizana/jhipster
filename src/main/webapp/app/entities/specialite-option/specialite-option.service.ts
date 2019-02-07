import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISpecialiteOption } from 'app/shared/model/specialite-option.model';

type EntityResponseType = HttpResponse<ISpecialiteOption>;
type EntityArrayResponseType = HttpResponse<ISpecialiteOption[]>;

@Injectable({ providedIn: 'root' })
export class SpecialiteOptionService {
    public resourceUrl = SERVER_API_URL + 'api/specialite-options';

    constructor(protected http: HttpClient) {}

    create(specialiteOption: ISpecialiteOption): Observable<EntityResponseType> {
        return this.http.post<ISpecialiteOption>(this.resourceUrl, specialiteOption, { observe: 'response' });
    }

    update(specialiteOption: ISpecialiteOption): Observable<EntityResponseType> {
        return this.http.put<ISpecialiteOption>(this.resourceUrl, specialiteOption, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISpecialiteOption>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISpecialiteOption[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
