import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PedidoComponent } from './pedido.component';
import { PedidoDetailComponent } from './pedido-detail.component';
import { PedidoPopupComponent } from './pedido-dialog.component';
import { PedidoDeletePopupComponent } from './pedido-delete-dialog.component';

export const pedidoRoute: Routes = [
    {
        path: 'pedido',
        component: PedidoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pedidos'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pedido/:id',
        component: PedidoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pedidos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pedidoPopupRoute: Routes = [
    {
        path: 'pedido-new',
        component: PedidoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pedidos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pedido/:id/edit',
        component: PedidoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pedidos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pedido/:id/delete',
        component: PedidoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pedidos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
