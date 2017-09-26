import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ItemPedido } from './item-pedido.model';
import { ItemPedidoPopupService } from './item-pedido-popup.service';
import { ItemPedidoService } from './item-pedido.service';

@Component({
    selector: 'jhi-item-pedido-delete-dialog',
    templateUrl: './item-pedido-delete-dialog.component.html'
})
export class ItemPedidoDeleteDialogComponent {

    itemPedido: ItemPedido;

    constructor(
        private itemPedidoService: ItemPedidoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.itemPedidoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'itemPedidoListModification',
                content: 'Deleted an itemPedido'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-item-pedido-delete-popup',
    template: ''
})
export class ItemPedidoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private itemPedidoPopupService: ItemPedidoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.itemPedidoPopupService
                .open(ItemPedidoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
