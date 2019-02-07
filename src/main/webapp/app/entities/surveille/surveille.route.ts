import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Surveille } from 'app/shared/model/surveille.model';
import { SurveilleService } from './surveille.service';
import { SurveilleComponent } from './surveille.component';
import { SurveilleDetailComponent } from './surveille-detail.component';
import { SurveilleUpdateComponent } from './surveille-update.component';
import { SurveilleDeletePopupComponent } from './surveille-delete-dialog.component';
import { ISurveille } from 'app/shared/model/surveille.model';

@Injectable({ providedIn: 'root' })
export class SurveilleResolve implements Resolve<ISurveille> {
    constructor(private service: SurveilleService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISurveille> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Surveille>) => response.ok),
                map((surveille: HttpResponse<Surveille>) => surveille.body)
            );
        }
        return of(new Surveille());
    }
}

export const surveilleRoute: Routes = [
    {
        path: '',
        component: SurveilleComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.surveille.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SurveilleDetailComponent,
        resolve: {
            surveille: SurveilleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.surveille.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SurveilleUpdateComponent,
        resolve: {
            surveille: SurveilleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.surveille.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SurveilleUpdateComponent,
        resolve: {
            surveille: SurveilleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.surveille.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const surveillePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SurveilleDeletePopupComponent,
        resolve: {
            surveille: SurveilleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.surveille.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
