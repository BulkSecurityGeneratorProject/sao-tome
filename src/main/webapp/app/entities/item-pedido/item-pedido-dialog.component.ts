import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ItemPedido } from './item-pedido.model';
import { ItemPedidoPopupService } from './item-pedido-popup.service';
import { ItemPedidoService } from './item-pedido.service';
import { Produto, ProdutoService } from '../produto';
import { Comanda, ComandaService } from '../comanda';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-item-pedido-dialog',
    templateUrl: './item-pedido-dialog.component.html'
})

export class ItemPedidoDialogComponent implements OnInit {

    itemPedido: ItemPedido;
    itemPedidos: ItemPedido[] = [];

    isSaving: boolean;

    comanda: Comanda;
    comandas: Comanda[];

    produtos: Produto[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private itemPedidoService: ItemPedidoService,
        private produtoService: ProdutoService,
        private comandaService: ComandaService,
        private eventManager: JhiEventManager
    ) { }

    ngOnInit() {
        this.isSaving = false;
        this.comandaService.query()
            .subscribe((res: ResponseWrapper) => { this.comandas = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.produtoService.query()
            .subscribe((res: ResponseWrapper) => { this.produtos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    add() {
        this.itemPedidos.push(Object.assign({}, this.itemPedido));
        this.itemPedido.produto = null;
        this.itemPedido.quantidade = null;
    }

    save() {
        this.isSaving = true;
        if (this.itemPedido.id !== undefined) {
            this.subscribeToSaveResponse(
                this.itemPedidoService.update(this.itemPedido));
        } else {
            this.subscribeToSaveResponse(
                this.itemPedidoService.create(this.itemPedido));
        }
    }

    private subscribeToSaveResponse(result: Observable<ItemPedido>) {
        result.subscribe((res: ItemPedido) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ItemPedido) {
        this.eventManager.broadcast({ name: 'itemPedidoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackComandaById(index: number, item: Comanda) {
        return item.id;
    }

    trackProdutoById(index: number, item: Produto) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-item-pedido-popup',
    template: ''
})
export class ItemPedidoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private itemPedidoPopupService: ItemPedidoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.itemPedidoPopupService
                    .open(ItemPedidoDialogComponent as Component, params['id']);
            } else {
                this.itemPedidoPopupService
                    .open(ItemPedidoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
