import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SpecialiteOption } from 'app/shared/model/specialite-option.model';
import { SpecialiteOptionService } from './specialite-option.service';
import { SpecialiteOptionComponent } from './specialite-option.component';
import { SpecialiteOptionDetailComponent } from './specialite-option-detail.component';
import { SpecialiteOptionUpdateComponent } from './specialite-option-update.component';
import { SpecialiteOptionDeletePopupComponent } from './specialite-option-delete-dialog.component';
import { ISpecialiteOption } from 'app/shared/model/specialite-option.model';

@Injectable({ providedIn: 'root' })
export class SpecialiteOptionResolve implements Resolve<ISpecialiteOption> {
    constructor(private service: SpecialiteOptionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISpecialiteOption> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SpecialiteOption>) => response.ok),
                map((specialiteOption: HttpResponse<SpecialiteOption>) => specialiteOption.body)
            );
        }
        return of(new SpecialiteOption());
    }
}

export const specialiteOptionRoute: Routes = [
    {
        path: '',
        component: SpecialiteOptionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.specialiteOption.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SpecialiteOptionDetailComponent,
        resolve: {
            specialiteOption: SpecialiteOptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.specialiteOption.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SpecialiteOptionUpdateComponent,
        resolve: {
            specialiteOption: SpecialiteOptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.specialiteOption.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SpecialiteOptionUpdateComponent,
        resolve: {
            specialiteOption: SpecialiteOptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.specialiteOption.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const specialiteOptionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SpecialiteOptionDeletePopupComponent,
        resolve: {
            specialiteOption: SpecialiteOptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.specialiteOption.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
