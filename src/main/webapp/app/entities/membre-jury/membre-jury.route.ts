import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MembreJury } from 'app/shared/model/membre-jury.model';
import { MembreJuryService } from './membre-jury.service';
import { MembreJuryComponent } from './membre-jury.component';
import { MembreJuryDetailComponent } from './membre-jury-detail.component';
import { MembreJuryUpdateComponent } from './membre-jury-update.component';
import { MembreJuryDeletePopupComponent } from './membre-jury-delete-dialog.component';
import { IMembreJury } from 'app/shared/model/membre-jury.model';

@Injectable({ providedIn: 'root' })
export class MembreJuryResolve implements Resolve<IMembreJury> {
    constructor(private service: MembreJuryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMembreJury> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<MembreJury>) => response.ok),
                map((membreJury: HttpResponse<MembreJury>) => membreJury.body)
            );
        }
        return of(new MembreJury());
    }
}

export const membreJuryRoute: Routes = [
    {
        path: '',
        component: MembreJuryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.membreJury.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MembreJuryDetailComponent,
        resolve: {
            membreJury: MembreJuryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.membreJury.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MembreJuryUpdateComponent,
        resolve: {
            membreJury: MembreJuryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.membreJury.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MembreJuryUpdateComponent,
        resolve: {
            membreJury: MembreJuryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.membreJury.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const membreJuryPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MembreJuryDeletePopupComponent,
        resolve: {
            membreJury: MembreJuryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.membreJury.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
