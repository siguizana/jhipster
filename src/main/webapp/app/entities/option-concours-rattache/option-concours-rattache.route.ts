import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OptionConcoursRattache } from 'app/shared/model/option-concours-rattache.model';
import { OptionConcoursRattacheService } from './option-concours-rattache.service';
import { OptionConcoursRattacheComponent } from './option-concours-rattache.component';
import { OptionConcoursRattacheDetailComponent } from './option-concours-rattache-detail.component';
import { OptionConcoursRattacheUpdateComponent } from './option-concours-rattache-update.component';
import { OptionConcoursRattacheDeletePopupComponent } from './option-concours-rattache-delete-dialog.component';
import { IOptionConcoursRattache } from 'app/shared/model/option-concours-rattache.model';

@Injectable({ providedIn: 'root' })
export class OptionConcoursRattacheResolve implements Resolve<IOptionConcoursRattache> {
    constructor(private service: OptionConcoursRattacheService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOptionConcoursRattache> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<OptionConcoursRattache>) => response.ok),
                map((optionConcoursRattache: HttpResponse<OptionConcoursRattache>) => optionConcoursRattache.body)
            );
        }
        return of(new OptionConcoursRattache());
    }
}

export const optionConcoursRattacheRoute: Routes = [
    {
        path: '',
        component: OptionConcoursRattacheComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.optionConcoursRattache.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: OptionConcoursRattacheDetailComponent,
        resolve: {
            optionConcoursRattache: OptionConcoursRattacheResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.optionConcoursRattache.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: OptionConcoursRattacheUpdateComponent,
        resolve: {
            optionConcoursRattache: OptionConcoursRattacheResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.optionConcoursRattache.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: OptionConcoursRattacheUpdateComponent,
        resolve: {
            optionConcoursRattache: OptionConcoursRattacheResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.optionConcoursRattache.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const optionConcoursRattachePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: OptionConcoursRattacheDeletePopupComponent,
        resolve: {
            optionConcoursRattache: OptionConcoursRattacheResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.optionConcoursRattache.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
