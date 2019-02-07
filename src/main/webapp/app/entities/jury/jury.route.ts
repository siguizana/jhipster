import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Jury } from 'app/shared/model/jury.model';
import { JuryService } from './jury.service';
import { JuryComponent } from './jury.component';
import { JuryDetailComponent } from './jury-detail.component';
import { JuryUpdateComponent } from './jury-update.component';
import { JuryDeletePopupComponent } from './jury-delete-dialog.component';
import { IJury } from 'app/shared/model/jury.model';

@Injectable({ providedIn: 'root' })
export class JuryResolve implements Resolve<IJury> {
    constructor(private service: JuryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IJury> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Jury>) => response.ok),
                map((jury: HttpResponse<Jury>) => jury.body)
            );
        }
        return of(new Jury());
    }
}

export const juryRoute: Routes = [
    {
        path: '',
        component: JuryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.jury.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: JuryDetailComponent,
        resolve: {
            jury: JuryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.jury.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: JuryUpdateComponent,
        resolve: {
            jury: JuryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.jury.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: JuryUpdateComponent,
        resolve: {
            jury: JuryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.jury.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const juryPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: JuryDeletePopupComponent,
        resolve: {
            jury: JuryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.jury.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
