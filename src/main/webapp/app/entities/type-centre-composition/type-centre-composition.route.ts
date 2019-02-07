import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TypeCentreComposition } from 'app/shared/model/type-centre-composition.model';
import { TypeCentreCompositionService } from './type-centre-composition.service';
import { TypeCentreCompositionComponent } from './type-centre-composition.component';
import { TypeCentreCompositionDetailComponent } from './type-centre-composition-detail.component';
import { TypeCentreCompositionUpdateComponent } from './type-centre-composition-update.component';
import { TypeCentreCompositionDeletePopupComponent } from './type-centre-composition-delete-dialog.component';
import { ITypeCentreComposition } from 'app/shared/model/type-centre-composition.model';

@Injectable({ providedIn: 'root' })
export class TypeCentreCompositionResolve implements Resolve<ITypeCentreComposition> {
    constructor(private service: TypeCentreCompositionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITypeCentreComposition> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TypeCentreComposition>) => response.ok),
                map((typeCentreComposition: HttpResponse<TypeCentreComposition>) => typeCentreComposition.body)
            );
        }
        return of(new TypeCentreComposition());
    }
}

export const typeCentreCompositionRoute: Routes = [
    {
        path: '',
        component: TypeCentreCompositionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeCentreComposition.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TypeCentreCompositionDetailComponent,
        resolve: {
            typeCentreComposition: TypeCentreCompositionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeCentreComposition.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TypeCentreCompositionUpdateComponent,
        resolve: {
            typeCentreComposition: TypeCentreCompositionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeCentreComposition.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TypeCentreCompositionUpdateComponent,
        resolve: {
            typeCentreComposition: TypeCentreCompositionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeCentreComposition.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeCentreCompositionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TypeCentreCompositionDeletePopupComponent,
        resolve: {
            typeCentreComposition: TypeCentreCompositionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeCentreComposition.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
