import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TypeDiplome } from 'app/shared/model/type-diplome.model';
import { TypeDiplomeService } from './type-diplome.service';
import { TypeDiplomeComponent } from './type-diplome.component';
import { TypeDiplomeDetailComponent } from './type-diplome-detail.component';
import { TypeDiplomeUpdateComponent } from './type-diplome-update.component';
import { TypeDiplomeDeletePopupComponent } from './type-diplome-delete-dialog.component';
import { ITypeDiplome } from 'app/shared/model/type-diplome.model';

@Injectable({ providedIn: 'root' })
export class TypeDiplomeResolve implements Resolve<ITypeDiplome> {
    constructor(private service: TypeDiplomeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITypeDiplome> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TypeDiplome>) => response.ok),
                map((typeDiplome: HttpResponse<TypeDiplome>) => typeDiplome.body)
            );
        }
        return of(new TypeDiplome());
    }
}

export const typeDiplomeRoute: Routes = [
    {
        path: '',
        component: TypeDiplomeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeDiplome.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TypeDiplomeDetailComponent,
        resolve: {
            typeDiplome: TypeDiplomeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeDiplome.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TypeDiplomeUpdateComponent,
        resolve: {
            typeDiplome: TypeDiplomeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeDiplome.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TypeDiplomeUpdateComponent,
        resolve: {
            typeDiplome: TypeDiplomeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeDiplome.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeDiplomePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TypeDiplomeDeletePopupComponent,
        resolve: {
            typeDiplome: TypeDiplomeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeDiplome.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
