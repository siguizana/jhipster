import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Fraude } from 'app/shared/model/fraude.model';
import { FraudeService } from './fraude.service';
import { FraudeComponent } from './fraude.component';
import { FraudeDetailComponent } from './fraude-detail.component';
import { FraudeUpdateComponent } from './fraude-update.component';
import { FraudeDeletePopupComponent } from './fraude-delete-dialog.component';
import { IFraude } from 'app/shared/model/fraude.model';

@Injectable({ providedIn: 'root' })
export class FraudeResolve implements Resolve<IFraude> {
    constructor(private service: FraudeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFraude> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Fraude>) => response.ok),
                map((fraude: HttpResponse<Fraude>) => fraude.body)
            );
        }
        return of(new Fraude());
    }
}

export const fraudeRoute: Routes = [
    {
        path: '',
        component: FraudeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.fraude.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: FraudeDetailComponent,
        resolve: {
            fraude: FraudeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.fraude.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: FraudeUpdateComponent,
        resolve: {
            fraude: FraudeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.fraude.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: FraudeUpdateComponent,
        resolve: {
            fraude: FraudeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.fraude.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fraudePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: FraudeDeletePopupComponent,
        resolve: {
            fraude: FraudeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.fraude.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
