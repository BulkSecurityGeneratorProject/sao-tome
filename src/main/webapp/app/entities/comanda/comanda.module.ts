import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaotomeSharedModule } from '../../shared';
import {
    ComandaService,
    ComandaPopupService,
    ComandaComponent,
    ComandaDetailComponent,
    ComandaDialogComponent,
    ComandaPopupComponent,
    ComandaDeletePopupComponent,
    ComandaDeleteDialogComponent,
    comandaRoute,
    comandaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...comandaRoute,
    ...comandaPopupRoute,
];

@NgModule({
    imports: [
        SaotomeSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ComandaComponent,
        ComandaDetailComponent,
        ComandaDialogComponent,
        ComandaDeleteDialogComponent,
        ComandaPopupComponent,
        ComandaDeletePopupComponent,
    ],
    entryComponents: [
        ComandaComponent,
        ComandaDialogComponent,
        ComandaPopupComponent,
        ComandaDeleteDialogComponent,
        ComandaDeletePopupComponent,
    ],
    providers: [
        ComandaService,
        ComandaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaotomeComandaModule {}
