import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Absence } from 'app/shared/model/absence.model';
import { AbsenceService } from './absence.service';
import { AbsenceComponent } from './absence.component';
import { AbsenceDetailComponent } from './absence-detail.component';
import { AbsenceUpdateComponent } from './absence-update.component';
import { AbsenceDeletePopupComponent } from './absence-delete-dialog.component';
import { IAbsence } from 'app/shared/model/absence.model';

@Injectable({ providedIn: 'root' })
export class AbsenceResolve implements Resolve<IAbsence> {
    constructor(private service: AbsenceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAbsence> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Absence>) => response.ok),
                map((absence: HttpResponse<Absence>) => absence.body)
            );
        }
        return of(new Absence());
    }
}

export const absenceRoute: Routes = [
    {
        path: '',
        component: AbsenceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.absence.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AbsenceDetailComponent,
        resolve: {
            absence: AbsenceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.absence.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AbsenceUpdateComponent,
        resolve: {
            absence: AbsenceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.absence.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AbsenceUpdateComponent,
        resolve: {
            absence: AbsenceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.absence.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const absencePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AbsenceDeletePopupComponent,
        resolve: {
            absence: AbsenceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.absence.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
