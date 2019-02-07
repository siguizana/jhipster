import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EtapeExamen } from 'app/shared/model/etape-examen.model';
import { EtapeExamenService } from './etape-examen.service';
import { EtapeExamenComponent } from './etape-examen.component';
import { EtapeExamenDetailComponent } from './etape-examen-detail.component';
import { EtapeExamenUpdateComponent } from './etape-examen-update.component';
import { EtapeExamenDeletePopupComponent } from './etape-examen-delete-dialog.component';
import { IEtapeExamen } from 'app/shared/model/etape-examen.model';

@Injectable({ providedIn: 'root' })
export class EtapeExamenResolve implements Resolve<IEtapeExamen> {
    constructor(private service: EtapeExamenService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEtapeExamen> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<EtapeExamen>) => response.ok),
                map((etapeExamen: HttpResponse<EtapeExamen>) => etapeExamen.body)
            );
        }
        return of(new EtapeExamen());
    }
}

export const etapeExamenRoute: Routes = [
    {
        path: '',
        component: EtapeExamenComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.etapeExamen.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: EtapeExamenDetailComponent,
        resolve: {
            etapeExamen: EtapeExamenResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.etapeExamen.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: EtapeExamenUpdateComponent,
        resolve: {
            etapeExamen: EtapeExamenResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.etapeExamen.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: EtapeExamenUpdateComponent,
        resolve: {
            etapeExamen: EtapeExamenResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.etapeExamen.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const etapeExamenPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: EtapeExamenDeletePopupComponent,
        resolve: {
            etapeExamen: EtapeExamenResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.etapeExamen.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
