import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOptionConcoursRattache } from 'app/shared/model/option-concours-rattache.model';

type EntityResponseType = HttpResponse<IOptionConcoursRattache>;
type EntityArrayResponseType = HttpResponse<IOptionConcoursRattache[]>;

@Injectable({ providedIn: 'root' })
export class OptionConcoursRattacheService {
    public resourceUrl = SERVER_API_URL + 'api/option-concours-rattaches';

    constructor(protected http: HttpClient) {}

    create(optionConcoursRattache: IOptionConcoursRattache): Observable<EntityResponseType> {
        return this.http.post<IOptionConcoursRattache>(this.resourceUrl, optionConcoursRattache, { observe: 'response' });
    }

    update(optionConcoursRattache: IOptionConcoursRattache): Observable<EntityResponseType> {
        return this.http.put<IOptionConcoursRattache>(this.resourceUrl, optionConcoursRattache, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IOptionConcoursRattache>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IOptionConcoursRattache[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
