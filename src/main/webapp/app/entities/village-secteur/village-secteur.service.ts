import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVillageSecteur } from 'app/shared/model/village-secteur.model';

type EntityResponseType = HttpResponse<IVillageSecteur>;
type EntityArrayResponseType = HttpResponse<IVillageSecteur[]>;

@Injectable({ providedIn: 'root' })
export class VillageSecteurService {
    public resourceUrl = SERVER_API_URL + 'api/village-secteurs';

    constructor(protected http: HttpClient) {}

    create(villageSecteur: IVillageSecteur): Observable<EntityResponseType> {
        return this.http.post<IVillageSecteur>(this.resourceUrl, villageSecteur, { observe: 'response' });
    }

    update(villageSecteur: IVillageSecteur): Observable<EntityResponseType> {
        return this.http.put<IVillageSecteur>(this.resourceUrl, villageSecteur, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IVillageSecteur>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IVillageSecteur[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
