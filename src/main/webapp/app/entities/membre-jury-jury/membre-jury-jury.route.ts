import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MembreJuryJury } from 'app/shared/model/membre-jury-jury.model';
import { MembreJuryJuryService } from './membre-jury-jury.service';
import { MembreJuryJuryComponent } from './membre-jury-jury.component';
import { MembreJuryJuryDetailComponent } from './membre-jury-jury-detail.component';
import { MembreJuryJuryUpdateComponent } from './membre-jury-jury-update.component';
import { MembreJuryJuryDeletePopupComponent } from './membre-jury-jury-delete-dialog.component';
import { IMembreJuryJury } from 'app/shared/model/membre-jury-jury.model';

@Injectable({ providedIn: 'root' })
export class MembreJuryJuryResolve implements Resolve<IMembreJuryJury> {
    constructor(private service: MembreJuryJuryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMembreJuryJury> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<MembreJuryJury>) => response.ok),
                map((membreJuryJury: HttpResponse<MembreJuryJury>) => membreJuryJury.body)
            );
        }
        return of(new MembreJuryJury());
    }
}

export const membreJuryJuryRoute: Routes = [
    {
        path: '',
        component: MembreJuryJuryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.membreJuryJury.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MembreJuryJuryDetailComponent,
        resolve: {
            membreJuryJury: MembreJuryJuryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.membreJuryJury.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MembreJuryJuryUpdateComponent,
        resolve: {
            membreJuryJury: MembreJuryJuryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.membreJuryJury.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MembreJuryJuryUpdateComponent,
        resolve: {
            membreJuryJury: MembreJuryJuryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.membreJuryJury.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const membreJuryJuryPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MembreJuryJuryDeletePopupComponent,
        resolve: {
            membreJuryJury: MembreJuryJuryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.membreJuryJury.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
