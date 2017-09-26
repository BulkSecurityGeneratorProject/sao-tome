import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {  Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { ItemPedido } from './item-pedido.model';
import { ItemPedidoService } from './item-pedido.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-item-pedido',
    templateUrl: './item-pedido.component.html'
})

export class ItemPedidoComponent implements OnInit, OnDestroy {
    itemPedidos: ItemPedido[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private itemPedidoService: ItemPedidoService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) { }

    loadAll() {
        this.itemPedidoService.query().subscribe(
            (res: ResponseWrapper) => {
                this.itemPedidos = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInItemPedidos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ItemPedido) {
        return item.id;
    }

    registerChangeInItemPedidos() {
        this.eventSubscriber = this.eventManager.subscribe('itemPedidoListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
