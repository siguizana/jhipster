import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Mention } from 'app/shared/model/mention.model';
import { MentionService } from './mention.service';
import { MentionComponent } from './mention.component';
import { MentionDetailComponent } from './mention-detail.component';
import { MentionUpdateComponent } from './mention-update.component';
import { MentionDeletePopupComponent } from './mention-delete-dialog.component';
import { IMention } from 'app/shared/model/mention.model';

@Injectable({ providedIn: 'root' })
export class MentionResolve implements Resolve<IMention> {
    constructor(private service: MentionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMention> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Mention>) => response.ok),
                map((mention: HttpResponse<Mention>) => mention.body)
            );
        }
        return of(new Mention());
    }
}

export const mentionRoute: Routes = [
    {
        path: '',
        component: MentionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.mention.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MentionDetailComponent,
        resolve: {
            mention: MentionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.mention.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MentionUpdateComponent,
        resolve: {
            mention: MentionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.mention.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MentionUpdateComponent,
        resolve: {
            mention: MentionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.mention.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mentionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MentionDeletePopupComponent,
        resolve: {
            mention: MentionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.mention.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
