import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFraude } from 'app/shared/model/fraude.model';

type EntityResponseType = HttpResponse<IFraude>;
type EntityArrayResponseType = HttpResponse<IFraude[]>;

@Injectable({ providedIn: 'root' })
export class FraudeService {
    public resourceUrl = SERVER_API_URL + 'api/fraudes';

    constructor(protected http: HttpClient) {}

    create(fraude: IFraude): Observable<EntityResponseType> {
        return this.http.post<IFraude>(this.resourceUrl, fraude, { observe: 'response' });
    }

    update(fraude: IFraude): Observable<EntityResponseType> {
        return this.http.put<IFraude>(this.resourceUrl, fraude, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IFraude>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFraude[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
