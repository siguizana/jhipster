import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ZoneExamen } from 'app/shared/model/zone-examen.model';
import { ZoneExamenService } from './zone-examen.service';
import { ZoneExamenComponent } from './zone-examen.component';
import { ZoneExamenDetailComponent } from './zone-examen-detail.component';
import { ZoneExamenUpdateComponent } from './zone-examen-update.component';
import { ZoneExamenDeletePopupComponent } from './zone-examen-delete-dialog.component';
import { IZoneExamen } from 'app/shared/model/zone-examen.model';

@Injectable({ providedIn: 'root' })
export class ZoneExamenResolve implements Resolve<IZoneExamen> {
    constructor(private service: ZoneExamenService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IZoneExamen> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ZoneExamen>) => response.ok),
                map((zoneExamen: HttpResponse<ZoneExamen>) => zoneExamen.body)
            );
        }
        return of(new ZoneExamen());
    }
}

export const zoneExamenRoute: Routes = [
    {
        path: '',
        component: ZoneExamenComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.zoneExamen.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ZoneExamenDetailComponent,
        resolve: {
            zoneExamen: ZoneExamenResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.zoneExamen.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ZoneExamenUpdateComponent,
        resolve: {
            zoneExamen: ZoneExamenResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.zoneExamen.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ZoneExamenUpdateComponent,
        resolve: {
            zoneExamen: ZoneExamenResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.zoneExamen.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const zoneExamenPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ZoneExamenDeletePopupComponent,
        resolve: {
            zoneExamen: ZoneExamenResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.zoneExamen.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
