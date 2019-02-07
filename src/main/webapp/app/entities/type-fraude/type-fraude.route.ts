import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TypeFraude } from 'app/shared/model/type-fraude.model';
import { TypeFraudeService } from './type-fraude.service';
import { TypeFraudeComponent } from './type-fraude.component';
import { TypeFraudeDetailComponent } from './type-fraude-detail.component';
import { TypeFraudeUpdateComponent } from './type-fraude-update.component';
import { TypeFraudeDeletePopupComponent } from './type-fraude-delete-dialog.component';
import { ITypeFraude } from 'app/shared/model/type-fraude.model';

@Injectable({ providedIn: 'root' })
export class TypeFraudeResolve implements Resolve<ITypeFraude> {
    constructor(private service: TypeFraudeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITypeFraude> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TypeFraude>) => response.ok),
                map((typeFraude: HttpResponse<TypeFraude>) => typeFraude.body)
            );
        }
        return of(new TypeFraude());
    }
}

export const typeFraudeRoute: Routes = [
    {
        path: '',
        component: TypeFraudeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeFraude.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TypeFraudeDetailComponent,
        resolve: {
            typeFraude: TypeFraudeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeFraude.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TypeFraudeUpdateComponent,
        resolve: {
            typeFraude: TypeFraudeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeFraude.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TypeFraudeUpdateComponent,
        resolve: {
            typeFraude: TypeFraudeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeFraude.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeFraudePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TypeFraudeDeletePopupComponent,
        resolve: {
            typeFraude: TypeFraudeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeFraude.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
