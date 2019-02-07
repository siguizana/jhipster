import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EpreuveSpecialiteOption } from 'app/shared/model/epreuve-specialite-option.model';
import { EpreuveSpecialiteOptionService } from './epreuve-specialite-option.service';
import { EpreuveSpecialiteOptionComponent } from './epreuve-specialite-option.component';
import { EpreuveSpecialiteOptionDetailComponent } from './epreuve-specialite-option-detail.component';
import { EpreuveSpecialiteOptionUpdateComponent } from './epreuve-specialite-option-update.component';
import { EpreuveSpecialiteOptionDeletePopupComponent } from './epreuve-specialite-option-delete-dialog.component';
import { IEpreuveSpecialiteOption } from 'app/shared/model/epreuve-specialite-option.model';

@Injectable({ providedIn: 'root' })
export class EpreuveSpecialiteOptionResolve implements Resolve<IEpreuveSpecialiteOption> {
    constructor(private service: EpreuveSpecialiteOptionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEpreuveSpecialiteOption> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<EpreuveSpecialiteOption>) => response.ok),
                map((epreuveSpecialiteOption: HttpResponse<EpreuveSpecialiteOption>) => epreuveSpecialiteOption.body)
            );
        }
        return of(new EpreuveSpecialiteOption());
    }
}

export const epreuveSpecialiteOptionRoute: Routes = [
    {
        path: '',
        component: EpreuveSpecialiteOptionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.epreuveSpecialiteOption.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: EpreuveSpecialiteOptionDetailComponent,
        resolve: {
            epreuveSpecialiteOption: EpreuveSpecialiteOptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.epreuveSpecialiteOption.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: EpreuveSpecialiteOptionUpdateComponent,
        resolve: {
            epreuveSpecialiteOption: EpreuveSpecialiteOptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.epreuveSpecialiteOption.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: EpreuveSpecialiteOptionUpdateComponent,
        resolve: {
            epreuveSpecialiteOption: EpreuveSpecialiteOptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.epreuveSpecialiteOption.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const epreuveSpecialiteOptionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: EpreuveSpecialiteOptionDeletePopupComponent,
        resolve: {
            epreuveSpecialiteOption: EpreuveSpecialiteOptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.epreuveSpecialiteOption.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
