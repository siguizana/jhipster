import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TypeMembreJury } from 'app/shared/model/type-membre-jury.model';
import { TypeMembreJuryService } from './type-membre-jury.service';
import { TypeMembreJuryComponent } from './type-membre-jury.component';
import { TypeMembreJuryDetailComponent } from './type-membre-jury-detail.component';
import { TypeMembreJuryUpdateComponent } from './type-membre-jury-update.component';
import { TypeMembreJuryDeletePopupComponent } from './type-membre-jury-delete-dialog.component';
import { ITypeMembreJury } from 'app/shared/model/type-membre-jury.model';

@Injectable({ providedIn: 'root' })
export class TypeMembreJuryResolve implements Resolve<ITypeMembreJury> {
    constructor(private service: TypeMembreJuryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITypeMembreJury> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TypeMembreJury>) => response.ok),
                map((typeMembreJury: HttpResponse<TypeMembreJury>) => typeMembreJury.body)
            );
        }
        return of(new TypeMembreJury());
    }
}

export const typeMembreJuryRoute: Routes = [
    {
        path: '',
        component: TypeMembreJuryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeMembreJury.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TypeMembreJuryDetailComponent,
        resolve: {
            typeMembreJury: TypeMembreJuryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeMembreJury.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TypeMembreJuryUpdateComponent,
        resolve: {
            typeMembreJury: TypeMembreJuryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeMembreJury.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TypeMembreJuryUpdateComponent,
        resolve: {
            typeMembreJury: TypeMembreJuryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeMembreJury.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeMembreJuryPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TypeMembreJuryDeletePopupComponent,
        resolve: {
            typeMembreJury: TypeMembreJuryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.typeMembreJury.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
