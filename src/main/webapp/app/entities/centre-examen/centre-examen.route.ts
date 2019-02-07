import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CentreExamen } from 'app/shared/model/centre-examen.model';
import { CentreExamenService } from './centre-examen.service';
import { CentreExamenComponent } from './centre-examen.component';
import { CentreExamenDetailComponent } from './centre-examen-detail.component';
import { CentreExamenUpdateComponent } from './centre-examen-update.component';
import { CentreExamenDeletePopupComponent } from './centre-examen-delete-dialog.component';
import { ICentreExamen } from 'app/shared/model/centre-examen.model';

@Injectable({ providedIn: 'root' })
export class CentreExamenResolve implements Resolve<ICentreExamen> {
    constructor(private service: CentreExamenService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICentreExamen> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CentreExamen>) => response.ok),
                map((centreExamen: HttpResponse<CentreExamen>) => centreExamen.body)
            );
        }
        return of(new CentreExamen());
    }
}

export const centreExamenRoute: Routes = [
    {
        path: '',
        component: CentreExamenComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.centreExamen.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CentreExamenDetailComponent,
        resolve: {
            centreExamen: CentreExamenResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.centreExamen.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CentreExamenUpdateComponent,
        resolve: {
            centreExamen: CentreExamenResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.centreExamen.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CentreExamenUpdateComponent,
        resolve: {
            centreExamen: CentreExamenResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.centreExamen.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const centreExamenPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CentreExamenDeletePopupComponent,
        resolve: {
            centreExamen: CentreExamenResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.centreExamen.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
