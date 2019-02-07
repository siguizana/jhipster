import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { NoteMembreCritere } from 'app/shared/model/note-membre-critere.model';
import { NoteMembreCritereService } from './note-membre-critere.service';
import { NoteMembreCritereComponent } from './note-membre-critere.component';
import { NoteMembreCritereDetailComponent } from './note-membre-critere-detail.component';
import { NoteMembreCritereUpdateComponent } from './note-membre-critere-update.component';
import { NoteMembreCritereDeletePopupComponent } from './note-membre-critere-delete-dialog.component';
import { INoteMembreCritere } from 'app/shared/model/note-membre-critere.model';

@Injectable({ providedIn: 'root' })
export class NoteMembreCritereResolve implements Resolve<INoteMembreCritere> {
    constructor(private service: NoteMembreCritereService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<INoteMembreCritere> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<NoteMembreCritere>) => response.ok),
                map((noteMembreCritere: HttpResponse<NoteMembreCritere>) => noteMembreCritere.body)
            );
        }
        return of(new NoteMembreCritere());
    }
}

export const noteMembreCritereRoute: Routes = [
    {
        path: '',
        component: NoteMembreCritereComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.noteMembreCritere.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: NoteMembreCritereDetailComponent,
        resolve: {
            noteMembreCritere: NoteMembreCritereResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.noteMembreCritere.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: NoteMembreCritereUpdateComponent,
        resolve: {
            noteMembreCritere: NoteMembreCritereResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.noteMembreCritere.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: NoteMembreCritereUpdateComponent,
        resolve: {
            noteMembreCritere: NoteMembreCritereResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.noteMembreCritere.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const noteMembreCriterePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: NoteMembreCritereDeletePopupComponent,
        resolve: {
            noteMembreCritere: NoteMembreCritereResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.noteMembreCritere.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
