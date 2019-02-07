import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ConcoursRattache } from 'app/shared/model/concours-rattache.model';
import { ConcoursRattacheService } from './concours-rattache.service';
import { ConcoursRattacheComponent } from './concours-rattache.component';
import { ConcoursRattacheDetailComponent } from './concours-rattache-detail.component';
import { ConcoursRattacheUpdateComponent } from './concours-rattache-update.component';
import { ConcoursRattacheDeletePopupComponent } from './concours-rattache-delete-dialog.component';
import { IConcoursRattache } from 'app/shared/model/concours-rattache.model';

@Injectable({ providedIn: 'root' })
export class ConcoursRattacheResolve implements Resolve<IConcoursRattache> {
    constructor(private service: ConcoursRattacheService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IConcoursRattache> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ConcoursRattache>) => response.ok),
                map((concoursRattache: HttpResponse<ConcoursRattache>) => concoursRattache.body)
            );
        }
        return of(new ConcoursRattache());
    }
}

export const concoursRattacheRoute: Routes = [
    {
        path: '',
        component: ConcoursRattacheComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.concoursRattache.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ConcoursRattacheDetailComponent,
        resolve: {
            concoursRattache: ConcoursRattacheResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.concoursRattache.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ConcoursRattacheUpdateComponent,
        resolve: {
            concoursRattache: ConcoursRattacheResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.concoursRattache.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ConcoursRattacheUpdateComponent,
        resolve: {
            concoursRattache: ConcoursRattacheResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.concoursRattache.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const concoursRattachePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ConcoursRattacheDeletePopupComponent,
        resolve: {
            concoursRattache: ConcoursRattacheResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.concoursRattache.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
