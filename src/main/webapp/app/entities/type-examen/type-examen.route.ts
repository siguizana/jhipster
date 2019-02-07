import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TypeExamen } from 'app/shared/model/type-examen.model';
import { TypeExamenService } from './type-examen.service';
import { TypeExamenComponent } from './type-examen.component';
import { TypeExamenDetailComponent } from './type-examen-detail.component';
import { TypeExamenUpdateComponent } from './type-examen-update.component';
import { TypeExamenDeletePopupComponent } from './type-examen-delete-dialog.component';
import { ITypeExamen } from 'app/shared/model/type-examen.model';

@Injectable({ providedIn: 'root' })
export class TypeExamenResolve implements Resolve<ITypeExamen> {
    constructor(private service: TypeExamenService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITypeExamen> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TypeExamen>) => response.ok),
                map((typeExamen: HttpResponse<TypeExamen>) => typeExamen.body)
            );
        }
        return of(new TypeExamen());
    }
}

export const typeExamenRoute: Routes = [
    {
        path: '',
        component: TypeExamenComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeExamen.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TypeExamenDetailComponent,
        resolve: {
            typeExamen: TypeExamenResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeExamen.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TypeExamenUpdateComponent,
        resolve: {
            typeExamen: TypeExamenResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeExamen.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TypeExamenUpdateComponent,
        resolve: {
            typeExamen: TypeExamenResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeExamen.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeExamenPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TypeExamenDeletePopupComponent,
        resolve: {
            typeExamen: TypeExamenResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeExamen.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
