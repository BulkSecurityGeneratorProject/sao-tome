import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaotomeSharedModule } from '../../shared';
import {
    EstoqueService,
    EstoquePopupService,
    EstoqueComponent,
    EstoqueDetailComponent,
    EstoqueDialogComponent,
    EstoquePopupComponent,
    EstoqueDeletePopupComponent,
    EstoqueDeleteDialogComponent,
    estoqueRoute,
    estoquePopupRoute,
} from './';

const ENTITY_STATES = [
    ...estoqueRoute,
    ...estoquePopupRoute,
];

@NgModule({
    imports: [
        SaotomeSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EstoqueComponent,
        EstoqueDetailComponent,
        EstoqueDialogComponent,
        EstoqueDeleteDialogComponent,
        EstoquePopupComponent,
        EstoqueDeletePopupComponent,
    ],
    entryComponents: [
        EstoqueComponent,
        EstoqueDialogComponent,
        EstoquePopupComponent,
        EstoqueDeleteDialogComponent,
        EstoqueDeletePopupComponent,
    ],
    providers: [
        EstoqueService,
        EstoquePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaotomeEstoqueModule {}
