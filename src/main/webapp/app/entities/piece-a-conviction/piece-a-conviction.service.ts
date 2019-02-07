import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPieceAConviction } from 'app/shared/model/piece-a-conviction.model';

type EntityResponseType = HttpResponse<IPieceAConviction>;
type EntityArrayResponseType = HttpResponse<IPieceAConviction[]>;

@Injectable({ providedIn: 'root' })
export class PieceAConvictionService {
    public resourceUrl = SERVER_API_URL + 'api/piece-a-convictions';

    constructor(protected http: HttpClient) {}

    create(pieceAConviction: IPieceAConviction): Observable<EntityResponseType> {
        return this.http.post<IPieceAConviction>(this.resourceUrl, pieceAConviction, { observe: 'response' });
    }

    update(pieceAConviction: IPieceAConviction): Observable<EntityResponseType> {
        return this.http.put<IPieceAConviction>(this.resourceUrl, pieceAConviction, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPieceAConviction>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPieceAConviction[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
