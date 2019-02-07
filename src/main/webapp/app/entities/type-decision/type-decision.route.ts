import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TypeDecision } from 'app/shared/model/type-decision.model';
import { TypeDecisionService } from './type-decision.service';
import { TypeDecisionComponent } from './type-decision.component';
import { TypeDecisionDetailComponent } from './type-decision-detail.component';
import { TypeDecisionUpdateComponent } from './type-decision-update.component';
import { TypeDecisionDeletePopupComponent } from './type-decision-delete-dialog.component';
import { ITypeDecision } from 'app/shared/model/type-decision.model';

@Injectable({ providedIn: 'root' })
export class TypeDecisionResolve implements Resolve<ITypeDecision> {
    constructor(private service: TypeDecisionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITypeDecision> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TypeDecision>) => response.ok),
                map((typeDecision: HttpResponse<TypeDecision>) => typeDecision.body)
            );
        }
        return of(new TypeDecision());
    }
}

export const typeDecisionRoute: Routes = [
    {
        path: '',
        component: TypeDecisionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeDecision.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TypeDecisionDetailComponent,
        resolve: {
            typeDecision: TypeDecisionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeDecision.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TypeDecisionUpdateComponent,
        resolve: {
            typeDecision: TypeDecisionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeDecision.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TypeDecisionUpdateComponent,
        resolve: {
            typeDecision: TypeDecisionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeDecision.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeDecisionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TypeDecisionDeletePopupComponent,
        resolve: {
            typeDecision: TypeDecisionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeDecision.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
