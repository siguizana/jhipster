import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Handicape } from 'app/shared/model/handicape.model';
import { HandicapeService } from './handicape.service';
import { HandicapeComponent } from './handicape.component';
import { HandicapeDetailComponent } from './handicape-detail.component';
import { HandicapeUpdateComponent } from './handicape-update.component';
import { HandicapeDeletePopupComponent } from './handicape-delete-dialog.component';
import { IHandicape } from 'app/shared/model/handicape.model';

@Injectable({ providedIn: 'root' })
export class HandicapeResolve implements Resolve<IHandicape> {
    constructor(private service: HandicapeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IHandicape> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Handicape>) => response.ok),
                map((handicape: HttpResponse<Handicape>) => handicape.body)
            );
        }
        return of(new Handicape());
    }
}

export const handicapeRoute: Routes = [
    {
        path: '',
        component: HandicapeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.handicape.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: HandicapeDetailComponent,
        resolve: {
            handicape: HandicapeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.handicape.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: HandicapeUpdateComponent,
        resolve: {
            handicape: HandicapeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.handicape.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: HandicapeUpdateComponent,
        resolve: {
            handicape: HandicapeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.handicape.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const handicapePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: HandicapeDeletePopupComponent,
        resolve: {
            handicape: HandicapeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.handicape.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
