import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Sanction } from 'app/shared/model/sanction.model';
import { SanctionService } from './sanction.service';
import { SanctionComponent } from './sanction.component';
import { SanctionDetailComponent } from './sanction-detail.component';
import { SanctionUpdateComponent } from './sanction-update.component';
import { SanctionDeletePopupComponent } from './sanction-delete-dialog.component';
import { ISanction } from 'app/shared/model/sanction.model';

@Injectable({ providedIn: 'root' })
export class SanctionResolve implements Resolve<ISanction> {
    constructor(private service: SanctionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISanction> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Sanction>) => response.ok),
                map((sanction: HttpResponse<Sanction>) => sanction.body)
            );
        }
        return of(new Sanction());
    }
}

export const sanctionRoute: Routes = [
    {
        path: '',
        component: SanctionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.sanction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SanctionDetailComponent,
        resolve: {
            sanction: SanctionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.sanction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SanctionUpdateComponent,
        resolve: {
            sanction: SanctionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.sanction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SanctionUpdateComponent,
        resolve: {
            sanction: SanctionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.sanction.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sanctionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SanctionDeletePopupComponent,
        resolve: {
            sanction: SanctionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.sanction.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
