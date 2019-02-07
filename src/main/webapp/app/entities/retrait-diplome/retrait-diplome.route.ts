import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RetraitDiplome } from 'app/shared/model/retrait-diplome.model';
import { RetraitDiplomeService } from './retrait-diplome.service';
import { RetraitDiplomeComponent } from './retrait-diplome.component';
import { RetraitDiplomeDetailComponent } from './retrait-diplome-detail.component';
import { RetraitDiplomeUpdateComponent } from './retrait-diplome-update.component';
import { RetraitDiplomeDeletePopupComponent } from './retrait-diplome-delete-dialog.component';
import { IRetraitDiplome } from 'app/shared/model/retrait-diplome.model';

@Injectable({ providedIn: 'root' })
export class RetraitDiplomeResolve implements Resolve<IRetraitDiplome> {
    constructor(private service: RetraitDiplomeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRetraitDiplome> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RetraitDiplome>) => response.ok),
                map((retraitDiplome: HttpResponse<RetraitDiplome>) => retraitDiplome.body)
            );
        }
        return of(new RetraitDiplome());
    }
}

export const retraitDiplomeRoute: Routes = [
    {
        path: '',
        component: RetraitDiplomeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.retraitDiplome.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RetraitDiplomeDetailComponent,
        resolve: {
            retraitDiplome: RetraitDiplomeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.retraitDiplome.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RetraitDiplomeUpdateComponent,
        resolve: {
            retraitDiplome: RetraitDiplomeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.retraitDiplome.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RetraitDiplomeUpdateComponent,
        resolve: {
            retraitDiplome: RetraitDiplomeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.retraitDiplome.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const retraitDiplomePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RetraitDiplomeDeletePopupComponent,
        resolve: {
            retraitDiplome: RetraitDiplomeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.retraitDiplome.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
