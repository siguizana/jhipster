import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { INoteMembreCritere } from 'app/shared/model/note-membre-critere.model';

type EntityResponseType = HttpResponse<INoteMembreCritere>;
type EntityArrayResponseType = HttpResponse<INoteMembreCritere[]>;

@Injectable({ providedIn: 'root' })
export class NoteMembreCritereService {
    public resourceUrl = SERVER_API_URL + 'api/note-membre-criteres';

    constructor(protected http: HttpClient) {}

    create(noteMembreCritere: INoteMembreCritere): Observable<EntityResponseType> {
        return this.http.post<INoteMembreCritere>(this.resourceUrl, noteMembreCritere, { observe: 'response' });
    }

    update(noteMembreCritere: INoteMembreCritere): Observable<EntityResponseType> {
        return this.http.put<INoteMembreCritere>(this.resourceUrl, noteMembreCritere, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<INoteMembreCritere>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<INoteMembreCritere[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
