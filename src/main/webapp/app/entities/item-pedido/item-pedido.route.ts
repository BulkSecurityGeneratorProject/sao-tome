import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ItemPedidoComponent } from './item-pedido.component';
import { ItemPedidoDetailComponent } from './item-pedido-detail.component';
import { ItemPedidoPopupComponent } from './item-pedido-dialog.component';
import { ItemPedidoDeletePopupComponent } from './item-pedido-delete-dialog.component';

export const itemPedidoRoute: Routes = [
    {
        path: 'item-pedido',
        component: ItemPedidoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemPedidos'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'item-pedido/:id',
        component: ItemPedidoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemPedidos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const itemPedidoPopupRoute: Routes = [
    {
        path: 'item-pedido-new',
        component: ItemPedidoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemPedidos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'item-pedido/:id/edit',
        component: ItemPedidoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemPedidos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'item-pedido/:id/delete',
        component: ItemPedidoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemPedidos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
