import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { VillageSecteur } from 'app/shared/model/village-secteur.model';
import { VillageSecteurService } from './village-secteur.service';
import { VillageSecteurComponent } from './village-secteur.component';
import { VillageSecteurDetailComponent } from './village-secteur-detail.component';
import { VillageSecteurUpdateComponent } from './village-secteur-update.component';
import { VillageSecteurDeletePopupComponent } from './village-secteur-delete-dialog.component';
import { IVillageSecteur } from 'app/shared/model/village-secteur.model';

@Injectable({ providedIn: 'root' })
export class VillageSecteurResolve implements Resolve<IVillageSecteur> {
    constructor(private service: VillageSecteurService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IVillageSecteur> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<VillageSecteur>) => response.ok),
                map((villageSecteur: HttpResponse<VillageSecteur>) => villageSecteur.body)
            );
        }
        return of(new VillageSecteur());
    }
}

export const villageSecteurRoute: Routes = [
    {
        path: '',
        component: VillageSecteurComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.villageSecteur.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: VillageSecteurDetailComponent,
        resolve: {
            villageSecteur: VillageSecteurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.villageSecteur.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: VillageSecteurUpdateComponent,
        resolve: {
            villageSecteur: VillageSecteurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.villageSecteur.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: VillageSecteurUpdateComponent,
        resolve: {
            villageSecteur: VillageSecteurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.villageSecteur.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const villageSecteurPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: VillageSecteurDeletePopupComponent,
        resolve: {
            villageSecteur: VillageSecteurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.villageSecteur.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
