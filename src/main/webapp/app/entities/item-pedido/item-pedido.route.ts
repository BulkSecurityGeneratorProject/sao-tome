import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ItemPedidoComponent } from './item-pedido.component';
import { ItemPedidoPopupComponent } from './item-pedido-dialog.component';

export const itemPedidoRoute: Routes = [
    {
        path: 'item-pedido',
        component: ItemPedidoComponent,
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
    }
];
