import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PieceAConviction } from 'app/shared/model/piece-a-conviction.model';
import { PieceAConvictionService } from './piece-a-conviction.service';
import { PieceAConvictionComponent } from './piece-a-conviction.component';
import { PieceAConvictionDetailComponent } from './piece-a-conviction-detail.component';
import { PieceAConvictionUpdateComponent } from './piece-a-conviction-update.component';
import { PieceAConvictionDeletePopupComponent } from './piece-a-conviction-delete-dialog.component';
import { IPieceAConviction } from 'app/shared/model/piece-a-conviction.model';

@Injectable({ providedIn: 'root' })
export class PieceAConvictionResolve implements Resolve<IPieceAConviction> {
    constructor(private service: PieceAConvictionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPieceAConviction> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PieceAConviction>) => response.ok),
                map((pieceAConviction: HttpResponse<PieceAConviction>) => pieceAConviction.body)
            );
        }
        return of(new PieceAConviction());
    }
}

export const pieceAConvictionRoute: Routes = [
    {
        path: '',
        component: PieceAConvictionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.pieceAConviction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PieceAConvictionDetailComponent,
        resolve: {
            pieceAConviction: PieceAConvictionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.pieceAConviction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PieceAConvictionUpdateComponent,
        resolve: {
            pieceAConviction: PieceAConvictionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.pieceAConviction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PieceAConvictionUpdateComponent,
        resolve: {
            pieceAConviction: PieceAConvictionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.pieceAConviction.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pieceAConvictionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PieceAConvictionDeletePopupComponent,
        resolve: {
            pieceAConviction: PieceAConvictionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sigecApp.pieceAConviction.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
