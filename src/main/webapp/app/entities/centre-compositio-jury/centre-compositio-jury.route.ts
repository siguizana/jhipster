import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CentreCompositioJury } from 'app/shared/model/centre-compositio-jury.model';
import { CentreCompositioJuryService } from './centre-compositio-jury.service';
import { CentreCompositioJuryComponent } from './centre-compositio-jury.component';
import { CentreCompositioJuryDetailComponent } from './centre-compositio-jury-detail.component';
import { CentreCompositioJuryUpdateComponent } from './centre-compositio-jury-update.component';
import { CentreCompositioJuryDeletePopupComponent } from './centre-compositio-jury-delete-dialog.component';
import { ICentreCompositioJury } from 'app/shared/model/centre-compositio-jury.model';

@Injectable({ providedIn: 'root' })
export class CentreCompositioJuryResolve implements Resolve<ICentreCompositioJury> {
    constructor(private service: CentreCompositioJuryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICentreCompositioJury> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CentreCompositioJury>) => response.ok),
                map((centreCompositioJury: HttpResponse<CentreCompositioJury>) => centreCompositioJury.body)
            );
        }
        return of(new CentreCompositioJury());
    }
}

export const centreCompositioJuryRoute: Routes = [
    {
        path: '',
        component: CentreCompositioJuryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.centreCompositioJury.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CentreCompositioJuryDetailComponent,
        resolve: {
            centreCompositioJury: CentreCompositioJuryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.centreCompositioJury.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CentreCompositioJuryUpdateComponent,
        resolve: {
            centreCompositioJury: CentreCompositioJuryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.centreCompositioJury.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CentreCompositioJuryUpdateComponent,
        resolve: {
            centreCompositioJury: CentreCompositioJuryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.centreCompositioJury.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const centreCompositioJuryPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CentreCompositioJuryDeletePopupComponent,
        resolve: {
            centreCompositioJury: CentreCompositioJuryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.centreCompositioJury.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
