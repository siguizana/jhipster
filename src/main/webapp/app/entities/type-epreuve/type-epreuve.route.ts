import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TypeEpreuve } from 'app/shared/model/type-epreuve.model';
import { TypeEpreuveService } from './type-epreuve.service';
import { TypeEpreuveComponent } from './type-epreuve.component';
import { TypeEpreuveDetailComponent } from './type-epreuve-detail.component';
import { TypeEpreuveUpdateComponent } from './type-epreuve-update.component';
import { TypeEpreuveDeletePopupComponent } from './type-epreuve-delete-dialog.component';
import { ITypeEpreuve } from 'app/shared/model/type-epreuve.model';

@Injectable({ providedIn: 'root' })
export class TypeEpreuveResolve implements Resolve<ITypeEpreuve> {
    constructor(private service: TypeEpreuveService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITypeEpreuve> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TypeEpreuve>) => response.ok),
                map((typeEpreuve: HttpResponse<TypeEpreuve>) => typeEpreuve.body)
            );
        }
        return of(new TypeEpreuve());
    }
}

export const typeEpreuveRoute: Routes = [
    {
        path: '',
        component: TypeEpreuveComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeEpreuve.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TypeEpreuveDetailComponent,
        resolve: {
            typeEpreuve: TypeEpreuveResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeEpreuve.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TypeEpreuveUpdateComponent,
        resolve: {
            typeEpreuve: TypeEpreuveResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeEpreuve.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TypeEpreuveUpdateComponent,
        resolve: {
            typeEpreuve: TypeEpreuveResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeEpreuve.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeEpreuvePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TypeEpreuveDeletePopupComponent,
        resolve: {
            typeEpreuve: TypeEpreuveResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeEpreuve.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
