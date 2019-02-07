import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICompositionCopie } from 'app/shared/model/composition-copie.model';

type EntityResponseType = HttpResponse<ICompositionCopie>;
type EntityArrayResponseType = HttpResponse<ICompositionCopie[]>;

@Injectable({ providedIn: 'root' })
export class CompositionCopieService {
    public resourceUrl = SERVER_API_URL + 'api/composition-copies';

    constructor(protected http: HttpClient) {}

    create(compositionCopie: ICompositionCopie): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(compositionCopie);
        return this.http
            .post<ICompositionCopie>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(compositionCopie: ICompositionCopie): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(compositionCopie);
        return this.http
            .put<ICompositionCopie>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICompositionCopie>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICompositionCopie[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(compositionCopie: ICompositionCopie): ICompositionCopie {
        const copy: ICompositionCopie = Object.assign({}, compositionCopie, {
            dateComposition:
                compositionCopie.dateComposition != null && compositionCopie.dateComposition.isValid()
                    ? compositionCopie.dateComposition.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateComposition = res.body.dateComposition != null ? moment(res.body.dateComposition) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((compositionCopie: ICompositionCopie) => {
                compositionCopie.dateComposition =
                    compositionCopie.dateComposition != null ? moment(compositionCopie.dateComposition) : null;
            });
        }
        return res;
    }
}
