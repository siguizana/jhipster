import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Reclamation } from 'app/shared/model/reclamation.model';
import { ReclamationService } from './reclamation.service';
import { ReclamationComponent } from './reclamation.component';
import { ReclamationDetailComponent } from './reclamation-detail.component';
import { ReclamationUpdateComponent } from './reclamation-update.component';
import { ReclamationDeletePopupComponent } from './reclamation-delete-dialog.component';
import { IReclamation } from 'app/shared/model/reclamation.model';

@Injectable({ providedIn: 'root' })
export class ReclamationResolve implements Resolve<IReclamation> {
    constructor(private service: ReclamationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IReclamation> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Reclamation>) => response.ok),
                map((reclamation: HttpResponse<Reclamation>) => reclamation.body)
            );
        }
        return of(new Reclamation());
    }
}

export const reclamationRoute: Routes = [
    {
        path: '',
        component: ReclamationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.reclamation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ReclamationDetailComponent,
        resolve: {
            reclamation: ReclamationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.reclamation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ReclamationUpdateComponent,
        resolve: {
            reclamation: ReclamationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.reclamation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ReclamationUpdateComponent,
        resolve: {
            reclamation: ReclamationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.reclamation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const reclamationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ReclamationDeletePopupComponent,
        resolve: {
            reclamation: ReclamationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.reclamation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
