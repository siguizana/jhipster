import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SalleComposition } from 'app/shared/model/salle-composition.model';
import { SalleCompositionService } from './salle-composition.service';
import { SalleCompositionComponent } from './salle-composition.component';
import { SalleCompositionDetailComponent } from './salle-composition-detail.component';
import { SalleCompositionUpdateComponent } from './salle-composition-update.component';
import { SalleCompositionDeletePopupComponent } from './salle-composition-delete-dialog.component';
import { ISalleComposition } from 'app/shared/model/salle-composition.model';

@Injectable({ providedIn: 'root' })
export class SalleCompositionResolve implements Resolve<ISalleComposition> {
    constructor(private service: SalleCompositionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISalleComposition> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SalleComposition>) => response.ok),
                map((salleComposition: HttpResponse<SalleComposition>) => salleComposition.body)
            );
        }
        return of(new SalleComposition());
    }
}

export const salleCompositionRoute: Routes = [
    {
        path: '',
        component: SalleCompositionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.salleComposition.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SalleCompositionDetailComponent,
        resolve: {
            salleComposition: SalleCompositionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.salleComposition.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SalleCompositionUpdateComponent,
        resolve: {
            salleComposition: SalleCompositionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.salleComposition.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SalleCompositionUpdateComponent,
        resolve: {
            salleComposition: SalleCompositionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.salleComposition.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const salleCompositionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SalleCompositionDeletePopupComponent,
        resolve: {
            salleComposition: SalleCompositionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.salleComposition.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
