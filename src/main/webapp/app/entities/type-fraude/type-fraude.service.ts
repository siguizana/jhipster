import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITypeFraude } from 'app/shared/model/type-fraude.model';

type EntityResponseType = HttpResponse<ITypeFraude>;
type EntityArrayResponseType = HttpResponse<ITypeFraude[]>;

@Injectable({ providedIn: 'root' })
export class TypeFraudeService {
    public resourceUrl = SERVER_API_URL + 'api/type-fraudes';

    constructor(protected http: HttpClient) {}

    create(typeFraude: ITypeFraude): Observable<EntityResponseType> {
        return this.http.post<ITypeFraude>(this.resourceUrl, typeFraude, { observe: 'response' });
    }

    update(typeFraude: ITypeFraude): Observable<EntityResponseType> {
        return this.http.put<ITypeFraude>(this.resourceUrl, typeFraude, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITypeFraude>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITypeFraude[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
