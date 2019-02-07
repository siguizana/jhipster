import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CentreComposition } from 'app/shared/model/centre-composition.model';
import { CentreCompositionService } from './centre-composition.service';
import { CentreCompositionComponent } from './centre-composition.component';
import { CentreCompositionDetailComponent } from './centre-composition-detail.component';
import { CentreCompositionUpdateComponent } from './centre-composition-update.component';
import { CentreCompositionDeletePopupComponent } from './centre-composition-delete-dialog.component';
import { ICentreComposition } from 'app/shared/model/centre-composition.model';

@Injectable({ providedIn: 'root' })
export class CentreCompositionResolve implements Resolve<ICentreComposition> {
    constructor(private service: CentreCompositionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICentreComposition> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CentreComposition>) => response.ok),
                map((centreComposition: HttpResponse<CentreComposition>) => centreComposition.body)
            );
        }
        return of(new CentreComposition());
    }
}

export const centreCompositionRoute: Routes = [
    {
        path: '',
        component: CentreCompositionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.centreComposition.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CentreCompositionDetailComponent,
        resolve: {
            centreComposition: CentreCompositionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.centreComposition.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CentreCompositionUpdateComponent,
        resolve: {
            centreComposition: CentreCompositionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.centreComposition.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CentreCompositionUpdateComponent,
        resolve: {
            centreComposition: CentreCompositionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.centreComposition.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const centreCompositionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CentreCompositionDeletePopupComponent,
        resolve: {
            centreComposition: CentreCompositionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.centreComposition.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
