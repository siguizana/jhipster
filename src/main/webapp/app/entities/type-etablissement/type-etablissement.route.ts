import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TypeEtablissement } from 'app/shared/model/type-etablissement.model';
import { TypeEtablissementService } from './type-etablissement.service';
import { TypeEtablissementComponent } from './type-etablissement.component';
import { TypeEtablissementDetailComponent } from './type-etablissement-detail.component';
import { TypeEtablissementUpdateComponent } from './type-etablissement-update.component';
import { TypeEtablissementDeletePopupComponent } from './type-etablissement-delete-dialog.component';
import { ITypeEtablissement } from 'app/shared/model/type-etablissement.model';

@Injectable({ providedIn: 'root' })
export class TypeEtablissementResolve implements Resolve<ITypeEtablissement> {
    constructor(private service: TypeEtablissementService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITypeEtablissement> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TypeEtablissement>) => response.ok),
                map((typeEtablissement: HttpResponse<TypeEtablissement>) => typeEtablissement.body)
            );
        }
        return of(new TypeEtablissement());
    }
}

export const typeEtablissementRoute: Routes = [
    {
        path: '',
        component: TypeEtablissementComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeEtablissement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TypeEtablissementDetailComponent,
        resolve: {
            typeEtablissement: TypeEtablissementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeEtablissement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TypeEtablissementUpdateComponent,
        resolve: {
            typeEtablissement: TypeEtablissementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeEtablissement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TypeEtablissementUpdateComponent,
        resolve: {
            typeEtablissement: TypeEtablissementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeEtablissement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeEtablissementPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TypeEtablissementDeletePopupComponent,
        resolve: {
            typeEtablissement: TypeEtablissementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeEtablissement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
