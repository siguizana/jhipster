import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IZoneExamen } from 'app/shared/model/zone-examen.model';

type EntityResponseType = HttpResponse<IZoneExamen>;
type EntityArrayResponseType = HttpResponse<IZoneExamen[]>;

@Injectable({ providedIn: 'root' })
export class ZoneExamenService {
    public resourceUrl = SERVER_API_URL + 'api/zone-examen';

    constructor(protected http: HttpClient) {}

    create(zoneExamen: IZoneExamen): Observable<EntityResponseType> {
        return this.http.post<IZoneExamen>(this.resourceUrl, zoneExamen, { observe: 'response' });
    }

    update(zoneExamen: IZoneExamen): Observable<EntityResponseType> {
        return this.http.put<IZoneExamen>(this.resourceUrl, zoneExamen, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IZoneExamen>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IZoneExamen[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
