import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISurveille } from 'app/shared/model/surveille.model';

type EntityResponseType = HttpResponse<ISurveille>;
type EntityArrayResponseType = HttpResponse<ISurveille[]>;

@Injectable({ providedIn: 'root' })
export class SurveilleService {
    public resourceUrl = SERVER_API_URL + 'api/surveilles';

    constructor(protected http: HttpClient) {}

    create(surveille: ISurveille): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(surveille);
        return this.http
            .post<ISurveille>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(surveille: ISurveille): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(surveille);
        return this.http
            .put<ISurveille>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISurveille>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISurveille[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(surveille: ISurveille): ISurveille {
        const copy: ISurveille = Object.assign({}, surveille, {
            dateDebutSurveillance:
                surveille.dateDebutSurveillance != null && surveille.dateDebutSurveillance.isValid()
                    ? surveille.dateDebutSurveillance.toJSON()
                    : null,
            dateFinSurveillance:
                surveille.dateFinSurveillance != null && surveille.dateFinSurveillance.isValid()
                    ? surveille.dateFinSurveillance.toJSON()
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateDebutSurveillance = res.body.dateDebutSurveillance != null ? moment(res.body.dateDebutSurveillance) : null;
            res.body.dateFinSurveillance = res.body.dateFinSurveillance != null ? moment(res.body.dateFinSurveillance) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((surveille: ISurveille) => {
                surveille.dateDebutSurveillance = surveille.dateDebutSurveillance != null ? moment(surveille.dateDebutSurveillance) : null;
                surveille.dateFinSurveillance = surveille.dateFinSurveillance != null ? moment(surveille.dateFinSurveillance) : null;
            });
        }
        return res;
    }
}
