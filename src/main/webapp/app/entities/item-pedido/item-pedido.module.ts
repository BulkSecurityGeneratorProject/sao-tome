import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaotomeSharedModule } from '../../shared';
import {
    ItemPedidoService,
    ItemPedidoPopupService,
    ItemPedidoComponent,
    ItemPedidoDialogComponent,
    ItemPedidoPopupComponent,
    itemPedidoRoute,
    itemPedidoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...itemPedidoRoute,
    ...itemPedidoPopupRoute,
];

@NgModule({
    imports: [
        SaotomeSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ItemPedidoComponent,
        ItemPedidoDialogComponent,
        ItemPedidoPopupComponent,
    ],
    entryComponents: [
        ItemPedidoComponent,
        ItemPedidoDialogComponent,
        ItemPedidoPopupComponent,
    ],
    providers: [
        ItemPedidoService,
        ItemPedidoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaotomeItemPedidoModule {}
