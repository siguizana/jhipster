import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Dispense } from 'app/shared/model/dispense.model';
import { DispenseService } from './dispense.service';
import { DispenseComponent } from './dispense.component';
import { DispenseDetailComponent } from './dispense-detail.component';
import { DispenseUpdateComponent } from './dispense-update.component';
import { DispenseDeletePopupComponent } from './dispense-delete-dialog.component';
import { IDispense } from 'app/shared/model/dispense.model';

@Injectable({ providedIn: 'root' })
export class DispenseResolve implements Resolve<IDispense> {
    constructor(private service: DispenseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDispense> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Dispense>) => response.ok),
                map((dispense: HttpResponse<Dispense>) => dispense.body)
            );
        }
        return of(new Dispense());
    }
}

export const dispenseRoute: Routes = [
    {
        path: '',
        component: DispenseComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.dispense.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DispenseDetailComponent,
        resolve: {
            dispense: DispenseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.dispense.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DispenseUpdateComponent,
        resolve: {
            dispense: DispenseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.dispense.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DispenseUpdateComponent,
        resolve: {
            dispense: DispenseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.dispense.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dispensePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DispenseDeletePopupComponent,
        resolve: {
            dispense: DispenseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.dispense.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
