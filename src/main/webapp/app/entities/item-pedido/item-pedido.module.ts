import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaotomeSharedModule } from '../../shared';
import {
    ItemPedidoService,
    ItemPedidoPopupService,
    ItemPedidoComponent,
    ItemPedidoDetailComponent,
    ItemPedidoDialogComponent,
    ItemPedidoPopupComponent,
    ItemPedidoDeletePopupComponent,
    ItemPedidoDeleteDialogComponent,
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
        ItemPedidoDetailComponent,
        ItemPedidoDialogComponent,
        ItemPedidoDeleteDialogComponent,
        ItemPedidoPopupComponent,
        ItemPedidoDeletePopupComponent,
    ],
    entryComponents: [
        ItemPedidoComponent,
        ItemPedidoDialogComponent,
        ItemPedidoPopupComponent,
        ItemPedidoDeleteDialogComponent,
        ItemPedidoDeletePopupComponent,
    ],
    providers: [
        ItemPedidoService,
        ItemPedidoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaotomeItemPedidoModule {}
