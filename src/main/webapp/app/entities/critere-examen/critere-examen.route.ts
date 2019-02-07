import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CritereExamen } from 'app/shared/model/critere-examen.model';
import { CritereExamenService } from './critere-examen.service';
import { CritereExamenComponent } from './critere-examen.component';
import { CritereExamenDetailComponent } from './critere-examen-detail.component';
import { CritereExamenUpdateComponent } from './critere-examen-update.component';
import { CritereExamenDeletePopupComponent } from './critere-examen-delete-dialog.component';
import { ICritereExamen } from 'app/shared/model/critere-examen.model';

@Injectable({ providedIn: 'root' })
export class CritereExamenResolve implements Resolve<ICritereExamen> {
    constructor(private service: CritereExamenService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICritereExamen> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CritereExamen>) => response.ok),
                map((critereExamen: HttpResponse<CritereExamen>) => critereExamen.body)
            );
        }
        return of(new CritereExamen());
    }
}

export const critereExamenRoute: Routes = [
    {
        path: '',
        component: CritereExamenComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.critereExamen.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CritereExamenDetailComponent,
        resolve: {
            critereExamen: CritereExamenResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.critereExamen.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CritereExamenUpdateComponent,
        resolve: {
            critereExamen: CritereExamenResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.critereExamen.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CritereExamenUpdateComponent,
        resolve: {
            critereExamen: CritereExamenResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.critereExamen.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const critereExamenPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CritereExamenDeletePopupComponent,
        resolve: {
            critereExamen: CritereExamenResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.critereExamen.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
