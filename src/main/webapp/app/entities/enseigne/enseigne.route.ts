import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Enseigne } from 'app/shared/model/enseigne.model';
import { EnseigneService } from './enseigne.service';
import { EnseigneComponent } from './enseigne.component';
import { EnseigneDetailComponent } from './enseigne-detail.component';
import { EnseigneUpdateComponent } from './enseigne-update.component';
import { EnseigneDeletePopupComponent } from './enseigne-delete-dialog.component';
import { IEnseigne } from 'app/shared/model/enseigne.model';

@Injectable({ providedIn: 'root' })
export class EnseigneResolve implements Resolve<IEnseigne> {
    constructor(private service: EnseigneService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEnseigne> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Enseigne>) => response.ok),
                map((enseigne: HttpResponse<Enseigne>) => enseigne.body)
            );
        }
        return of(new Enseigne());
    }
}

export const enseigneRoute: Routes = [
    {
        path: '',
        component: EnseigneComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.enseigne.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: EnseigneDetailComponent,
        resolve: {
            enseigne: EnseigneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.enseigne.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: EnseigneUpdateComponent,
        resolve: {
            enseigne: EnseigneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.enseigne.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: EnseigneUpdateComponent,
        resolve: {
            enseigne: EnseigneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.enseigne.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const enseignePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: EnseigneDeletePopupComponent,
        resolve: {
            enseigne: EnseigneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.enseigne.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
