import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Epreuve } from 'app/shared/model/epreuve.model';
import { EpreuveService } from './epreuve.service';
import { EpreuveComponent } from './epreuve.component';
import { EpreuveDetailComponent } from './epreuve-detail.component';
import { EpreuveUpdateComponent } from './epreuve-update.component';
import { EpreuveDeletePopupComponent } from './epreuve-delete-dialog.component';
import { IEpreuve } from 'app/shared/model/epreuve.model';

@Injectable({ providedIn: 'root' })
export class EpreuveResolve implements Resolve<IEpreuve> {
    constructor(private service: EpreuveService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEpreuve> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Epreuve>) => response.ok),
                map((epreuve: HttpResponse<Epreuve>) => epreuve.body)
            );
        }
        return of(new Epreuve());
    }
}

export const epreuveRoute: Routes = [
    {
        path: '',
        component: EpreuveComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.epreuve.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: EpreuveDetailComponent,
        resolve: {
            epreuve: EpreuveResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.epreuve.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: EpreuveUpdateComponent,
        resolve: {
            epreuve: EpreuveResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.epreuve.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: EpreuveUpdateComponent,
        resolve: {
            epreuve: EpreuveResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.epreuve.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const epreuvePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: EpreuveDeletePopupComponent,
        resolve: {
            epreuve: EpreuveResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.epreuve.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
