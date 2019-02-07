import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Ceb } from 'app/shared/model/ceb.model';
import { CebService } from './ceb.service';
import { CebComponent } from './ceb.component';
import { CebDetailComponent } from './ceb-detail.component';
import { CebUpdateComponent } from './ceb-update.component';
import { CebDeletePopupComponent } from './ceb-delete-dialog.component';
import { ICeb } from 'app/shared/model/ceb.model';

@Injectable({ providedIn: 'root' })
export class CebResolve implements Resolve<ICeb> {
    constructor(private service: CebService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICeb> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Ceb>) => response.ok),
                map((ceb: HttpResponse<Ceb>) => ceb.body)
            );
        }
        return of(new Ceb());
    }
}

export const cebRoute: Routes = [
    {
        path: '',
        component: CebComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.ceb.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CebDetailComponent,
        resolve: {
            ceb: CebResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.ceb.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CebUpdateComponent,
        resolve: {
            ceb: CebResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.ceb.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CebUpdateComponent,
        resolve: {
            ceb: CebResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.ceb.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cebPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CebDeletePopupComponent,
        resolve: {
            ceb: CebResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.ceb.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
