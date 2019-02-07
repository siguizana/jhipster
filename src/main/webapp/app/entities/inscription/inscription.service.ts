import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInscription } from 'app/shared/model/inscription.model';

type EntityResponseType = HttpResponse<IInscription>;
type EntityArrayResponseType = HttpResponse<IInscription[]>;

@Injectable({ providedIn: 'root' })
export class InscriptionService {
    public resourceUrl = SERVER_API_URL + 'api/inscriptions';

    constructor(protected http: HttpClient) {}

    create(inscription: IInscription): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(inscription);
        return this.http
            .post<IInscription>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(inscription: IInscription): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(inscription);
        return this.http
            .put<IInscription>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IInscription>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IInscription[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(inscription: IInscription): IInscription {
        const copy: IInscription = Object.assign({}, inscription, {
            dateInscription:
                inscription.dateInscription != null && inscription.dateInscription.isValid()
                    ? inscription.dateInscription.format(DATE_FORMAT)
                    : null,
            dateDernierePromotion:
                inscription.dateDernierePromotion != null && inscription.dateDernierePromotion.isValid()
                    ? inscription.dateDernierePromotion.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateInscription = res.body.dateInscription != null ? moment(res.body.dateInscription) : null;
            res.body.dateDernierePromotion = res.body.dateDernierePromotion != null ? moment(res.body.dateDernierePromotion) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((inscription: IInscription) => {
                inscription.dateInscription = inscription.dateInscription != null ? moment(inscription.dateInscription) : null;
                inscription.dateDernierePromotion =
                    inscription.dateDernierePromotion != null ? moment(inscription.dateDernierePromotion) : null;
            });
        }
        return res;
    }
}
