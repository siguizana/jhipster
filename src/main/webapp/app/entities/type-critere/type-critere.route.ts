import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TypeCritere } from 'app/shared/model/type-critere.model';
import { TypeCritereService } from './type-critere.service';
import { TypeCritereComponent } from './type-critere.component';
import { TypeCritereDetailComponent } from './type-critere-detail.component';
import { TypeCritereUpdateComponent } from './type-critere-update.component';
import { TypeCritereDeletePopupComponent } from './type-critere-delete-dialog.component';
import { ITypeCritere } from 'app/shared/model/type-critere.model';

@Injectable({ providedIn: 'root' })
export class TypeCritereResolve implements Resolve<ITypeCritere> {
    constructor(private service: TypeCritereService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITypeCritere> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TypeCritere>) => response.ok),
                map((typeCritere: HttpResponse<TypeCritere>) => typeCritere.body)
            );
        }
        return of(new TypeCritere());
    }
}

export const typeCritereRoute: Routes = [
    {
        path: '',
        component: TypeCritereComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeCritere.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TypeCritereDetailComponent,
        resolve: {
            typeCritere: TypeCritereResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeCritere.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TypeCritereUpdateComponent,
        resolve: {
            typeCritere: TypeCritereResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeCritere.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TypeCritereUpdateComponent,
        resolve: {
            typeCritere: TypeCritereResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeCritere.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeCriterePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TypeCritereDeletePopupComponent,
        resolve: {
            typeCritere: TypeCritereResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeCritere.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
