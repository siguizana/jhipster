import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { GroupeEpreuve } from 'app/shared/model/groupe-epreuve.model';
import { GroupeEpreuveService } from './groupe-epreuve.service';
import { GroupeEpreuveComponent } from './groupe-epreuve.component';
import { GroupeEpreuveDetailComponent } from './groupe-epreuve-detail.component';
import { GroupeEpreuveUpdateComponent } from './groupe-epreuve-update.component';
import { GroupeEpreuveDeletePopupComponent } from './groupe-epreuve-delete-dialog.component';
import { IGroupeEpreuve } from 'app/shared/model/groupe-epreuve.model';

@Injectable({ providedIn: 'root' })
export class GroupeEpreuveResolve implements Resolve<IGroupeEpreuve> {
    constructor(private service: GroupeEpreuveService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IGroupeEpreuve> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<GroupeEpreuve>) => response.ok),
                map((groupeEpreuve: HttpResponse<GroupeEpreuve>) => groupeEpreuve.body)
            );
        }
        return of(new GroupeEpreuve());
    }
}

export const groupeEpreuveRoute: Routes = [
    {
        path: '',
        component: GroupeEpreuveComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.groupeEpreuve.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: GroupeEpreuveDetailComponent,
        resolve: {
            groupeEpreuve: GroupeEpreuveResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.groupeEpreuve.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: GroupeEpreuveUpdateComponent,
        resolve: {
            groupeEpreuve: GroupeEpreuveResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.groupeEpreuve.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: GroupeEpreuveUpdateComponent,
        resolve: {
            groupeEpreuve: GroupeEpreuveResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.groupeEpreuve.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const groupeEpreuvePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: GroupeEpreuveDeletePopupComponent,
        resolve: {
            groupeEpreuve: GroupeEpreuveResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.groupeEpreuve.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
