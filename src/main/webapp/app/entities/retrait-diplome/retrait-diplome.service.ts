import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRetraitDiplome } from 'app/shared/model/retrait-diplome.model';

type EntityResponseType = HttpResponse<IRetraitDiplome>;
type EntityArrayResponseType = HttpResponse<IRetraitDiplome[]>;

@Injectable({ providedIn: 'root' })
export class RetraitDiplomeService {
    public resourceUrl = SERVER_API_URL + 'api/retrait-diplomes';

    constructor(protected http: HttpClient) {}

    create(retraitDiplome: IRetraitDiplome): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(retraitDiplome);
        return this.http
            .post<IRetraitDiplome>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(retraitDiplome: IRetraitDiplome): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(retraitDiplome);
        return this.http
            .put<IRetraitDiplome>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IRetraitDiplome>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRetraitDiplome[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(retraitDiplome: IRetraitDiplome): IRetraitDiplome {
        const copy: IRetraitDiplome = Object.assign({}, retraitDiplome, {
            dateRetrait:
                retraitDiplome.dateRetrait != null && retraitDiplome.dateRetrait.isValid()
                    ? retraitDiplome.dateRetrait.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateRetrait = res.body.dateRetrait != null ? moment(res.body.dateRetrait) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((retraitDiplome: IRetraitDiplome) => {
                retraitDiplome.dateRetrait = retraitDiplome.dateRetrait != null ? moment(retraitDiplome.dateRetrait) : null;
            });
        }
        return res;
    }
}
