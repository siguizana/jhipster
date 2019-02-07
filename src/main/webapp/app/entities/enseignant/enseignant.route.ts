import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Enseignant } from 'app/shared/model/enseignant.model';
import { EnseignantService } from './enseignant.service';
import { EnseignantComponent } from './enseignant.component';
import { EnseignantDetailComponent } from './enseignant-detail.component';
import { EnseignantUpdateComponent } from './enseignant-update.component';
import { EnseignantDeletePopupComponent } from './enseignant-delete-dialog.component';
import { IEnseignant } from 'app/shared/model/enseignant.model';

@Injectable({ providedIn: 'root' })
export class EnseignantResolve implements Resolve<IEnseignant> {
    constructor(private service: EnseignantService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEnseignant> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Enseignant>) => response.ok),
                map((enseignant: HttpResponse<Enseignant>) => enseignant.body)
            );
        }
        return of(new Enseignant());
    }
}

export const enseignantRoute: Routes = [
    {
        path: '',
        component: EnseignantComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.enseignant.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: EnseignantDetailComponent,
        resolve: {
            enseignant: EnseignantResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.enseignant.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: EnseignantUpdateComponent,
        resolve: {
            enseignant: EnseignantResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.enseignant.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: EnseignantUpdateComponent,
        resolve: {
            enseignant: EnseignantResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.enseignant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const enseignantPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: EnseignantDeletePopupComponent,
        resolve: {
            enseignant: EnseignantResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.enseignant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
