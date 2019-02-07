import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DeroulementScolarite } from 'app/shared/model/deroulement-scolarite.model';
import { DeroulementScolariteService } from './deroulement-scolarite.service';
import { DeroulementScolariteComponent } from './deroulement-scolarite.component';
import { DeroulementScolariteDetailComponent } from './deroulement-scolarite-detail.component';
import { DeroulementScolariteUpdateComponent } from './deroulement-scolarite-update.component';
import { DeroulementScolariteDeletePopupComponent } from './deroulement-scolarite-delete-dialog.component';
import { IDeroulementScolarite } from 'app/shared/model/deroulement-scolarite.model';

@Injectable({ providedIn: 'root' })
export class DeroulementScolariteResolve implements Resolve<IDeroulementScolarite> {
    constructor(private service: DeroulementScolariteService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDeroulementScolarite> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DeroulementScolarite>) => response.ok),
                map((deroulementScolarite: HttpResponse<DeroulementScolarite>) => deroulementScolarite.body)
            );
        }
        return of(new DeroulementScolarite());
    }
}

export const deroulementScolariteRoute: Routes = [
    {
        path: '',
        component: DeroulementScolariteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.deroulementScolarite.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DeroulementScolariteDetailComponent,
        resolve: {
            deroulementScolarite: DeroulementScolariteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.deroulementScolarite.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DeroulementScolariteUpdateComponent,
        resolve: {
            deroulementScolarite: DeroulementScolariteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.deroulementScolarite.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DeroulementScolariteUpdateComponent,
        resolve: {
            deroulementScolarite: DeroulementScolariteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.deroulementScolarite.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const deroulementScolaritePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DeroulementScolariteDeletePopupComponent,
        resolve: {
            deroulementScolarite: DeroulementScolariteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.deroulementScolarite.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
