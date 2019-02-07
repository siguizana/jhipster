import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ChoixEtablissement } from 'app/shared/model/choix-etablissement.model';
import { ChoixEtablissementService } from './choix-etablissement.service';
import { ChoixEtablissementComponent } from './choix-etablissement.component';
import { ChoixEtablissementDetailComponent } from './choix-etablissement-detail.component';
import { ChoixEtablissementUpdateComponent } from './choix-etablissement-update.component';
import { ChoixEtablissementDeletePopupComponent } from './choix-etablissement-delete-dialog.component';
import { IChoixEtablissement } from 'app/shared/model/choix-etablissement.model';

@Injectable({ providedIn: 'root' })
export class ChoixEtablissementResolve implements Resolve<IChoixEtablissement> {
    constructor(private service: ChoixEtablissementService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IChoixEtablissement> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ChoixEtablissement>) => response.ok),
                map((choixEtablissement: HttpResponse<ChoixEtablissement>) => choixEtablissement.body)
            );
        }
        return of(new ChoixEtablissement());
    }
}

export const choixEtablissementRoute: Routes = [
    {
        path: '',
        component: ChoixEtablissementComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.choixEtablissement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ChoixEtablissementDetailComponent,
        resolve: {
            choixEtablissement: ChoixEtablissementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.choixEtablissement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ChoixEtablissementUpdateComponent,
        resolve: {
            choixEtablissement: ChoixEtablissementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.choixEtablissement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ChoixEtablissementUpdateComponent,
        resolve: {
            choixEtablissement: ChoixEtablissementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.choixEtablissement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const choixEtablissementPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ChoixEtablissementDeletePopupComponent,
        resolve: {
            choixEtablissement: ChoixEtablissementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.choixEtablissement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
