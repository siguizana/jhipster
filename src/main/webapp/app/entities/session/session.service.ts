import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISession } from 'app/shared/model/session.model';

type EntityResponseType = HttpResponse<ISession>;
type EntityArrayResponseType = HttpResponse<ISession[]>;

@Injectable({ providedIn: 'root' })
export class SessionService {
    public resourceUrl = SERVER_API_URL + 'api/sessions';

    constructor(protected http: HttpClient) {}

    create(session: ISession): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(session);
        return this.http
            .post<ISession>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(session: ISession): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(session);
        return this.http
            .put<ISession>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISession>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISession[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(session: ISession): ISession {
        const copy: ISession = Object.assign({}, session, {
            dateOuverture:
                session.dateOuverture != null && session.dateOuverture.isValid() ? session.dateOuverture.format(DATE_FORMAT) : null,
            dateFermeture:
                session.dateFermeture != null && session.dateFermeture.isValid() ? session.dateFermeture.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateOuverture = res.body.dateOuverture != null ? moment(res.body.dateOuverture) : null;
            res.body.dateFermeture = res.body.dateFermeture != null ? moment(res.body.dateFermeture) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((session: ISession) => {
                session.dateOuverture = session.dateOuverture != null ? moment(session.dateOuverture) : null;
                session.dateFermeture = session.dateFermeture != null ? moment(session.dateFermeture) : null;
            });
        }
        return res;
    }
}
