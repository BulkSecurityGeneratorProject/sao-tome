import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { EstoqueComponent } from './estoque.component';
import { EstoqueDetailComponent } from './estoque-detail.component';
import { EstoquePopupComponent } from './estoque-dialog.component';
import { EstoqueDeletePopupComponent } from './estoque-delete-dialog.component';

export const estoqueRoute: Routes = [
    {
        path: 'estoque',
        component: EstoqueComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Estoques'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'estoque/:id',
        component: EstoqueDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Estoques'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const estoquePopupRoute: Routes = [
    {
        path: 'estoque-new',
        component: EstoquePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Estoques'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'estoque/:id/edit',
        component: EstoquePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Estoques'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'estoque/:id/delete',
        component: EstoqueDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Estoques'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
