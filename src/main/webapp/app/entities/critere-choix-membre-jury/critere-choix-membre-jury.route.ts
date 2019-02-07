import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CritereChoixMembreJury } from 'app/shared/model/critere-choix-membre-jury.model';
import { CritereChoixMembreJuryService } from './critere-choix-membre-jury.service';
import { CritereChoixMembreJuryComponent } from './critere-choix-membre-jury.component';
import { CritereChoixMembreJuryDetailComponent } from './critere-choix-membre-jury-detail.component';
import { CritereChoixMembreJuryUpdateComponent } from './critere-choix-membre-jury-update.component';
import { CritereChoixMembreJuryDeletePopupComponent } from './critere-choix-membre-jury-delete-dialog.component';
import { ICritereChoixMembreJury } from 'app/shared/model/critere-choix-membre-jury.model';

@Injectable({ providedIn: 'root' })
export class CritereChoixMembreJuryResolve implements Resolve<ICritereChoixMembreJury> {
    constructor(private service: CritereChoixMembreJuryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICritereChoixMembreJury> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CritereChoixMembreJury>) => response.ok),
                map((critereChoixMembreJury: HttpResponse<CritereChoixMembreJury>) => critereChoixMembreJury.body)
            );
        }
        return of(new CritereChoixMembreJury());
    }
}

export const critereChoixMembreJuryRoute: Routes = [
    {
        path: '',
        component: CritereChoixMembreJuryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.critereChoixMembreJury.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CritereChoixMembreJuryDetailComponent,
        resolve: {
            critereChoixMembreJury: CritereChoixMembreJuryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.critereChoixMembreJury.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CritereChoixMembreJuryUpdateComponent,
        resolve: {
            critereChoixMembreJury: CritereChoixMembreJuryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.critereChoixMembreJury.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CritereChoixMembreJuryUpdateComponent,
        resolve: {
            critereChoixMembreJury: CritereChoixMembreJuryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.critereChoixMembreJury.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const critereChoixMembreJuryPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CritereChoixMembreJuryDeletePopupComponent,
        resolve: {
            critereChoixMembreJury: CritereChoixMembreJuryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.critereChoixMembreJury.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
