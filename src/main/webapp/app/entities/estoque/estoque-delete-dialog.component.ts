import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Estoque } from './estoque.model';
import { EstoquePopupService } from './estoque-popup.service';
import { EstoqueService } from './estoque.service';

@Component({
    selector: 'jhi-estoque-delete-dialog',
    templateUrl: './estoque-delete-dialog.component.html'
})
export class EstoqueDeleteDialogComponent {

    estoque: Estoque;

    constructor(
        private estoqueService: EstoqueService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.estoqueService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'estoqueListModification',
                content: 'Deleted an estoque'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-estoque-delete-popup',
    template: ''
})
export class EstoqueDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private estoquePopupService: EstoquePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.estoquePopupService
                .open(EstoqueDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
