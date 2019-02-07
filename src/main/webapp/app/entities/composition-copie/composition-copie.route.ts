import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CompositionCopie } from 'app/shared/model/composition-copie.model';
import { CompositionCopieService } from './composition-copie.service';
import { CompositionCopieComponent } from './composition-copie.component';
import { CompositionCopieDetailComponent } from './composition-copie-detail.component';
import { CompositionCopieUpdateComponent } from './composition-copie-update.component';
import { CompositionCopieDeletePopupComponent } from './composition-copie-delete-dialog.component';
import { ICompositionCopie } from 'app/shared/model/composition-copie.model';

@Injectable({ providedIn: 'root' })
export class CompositionCopieResolve implements Resolve<ICompositionCopie> {
    constructor(private service: CompositionCopieService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICompositionCopie> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CompositionCopie>) => response.ok),
                map((compositionCopie: HttpResponse<CompositionCopie>) => compositionCopie.body)
            );
        }
        return of(new CompositionCopie());
    }
}

export const compositionCopieRoute: Routes = [
    {
        path: '',
        component: CompositionCopieComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.compositionCopie.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CompositionCopieDetailComponent,
        resolve: {
            compositionCopie: CompositionCopieResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.compositionCopie.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CompositionCopieUpdateComponent,
        resolve: {
            compositionCopie: CompositionCopieResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.compositionCopie.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CompositionCopieUpdateComponent,
        resolve: {
            compositionCopie: CompositionCopieResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.compositionCopie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const compositionCopiePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CompositionCopieDeletePopupComponent,
        resolve: {
            compositionCopie: CompositionCopieResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.compositionCopie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
