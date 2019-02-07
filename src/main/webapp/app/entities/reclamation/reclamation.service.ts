import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReclamation } from 'app/shared/model/reclamation.model';

type EntityResponseType = HttpResponse<IReclamation>;
type EntityArrayResponseType = HttpResponse<IReclamation[]>;

@Injectable({ providedIn: 'root' })
export class ReclamationService {
    public resourceUrl = SERVER_API_URL + 'api/reclamations';

    constructor(protected http: HttpClient) {}

    create(reclamation: IReclamation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(reclamation);
        return this.http
            .post<IReclamation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(reclamation: IReclamation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(reclamation);
        return this.http
            .put<IReclamation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IReclamation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IReclamation[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(reclamation: IReclamation): IReclamation {
        const copy: IReclamation = Object.assign({}, reclamation, {
            dateReclamation:
                reclamation.dateReclamation != null && reclamation.dateReclamation.isValid()
                    ? reclamation.dateReclamation.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateReclamation = res.body.dateReclamation != null ? moment(res.body.dateReclamation) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((reclamation: IReclamation) => {
                reclamation.dateReclamation = reclamation.dateReclamation != null ? moment(reclamation.dateReclamation) : null;
            });
        }
        return res;
    }
}
