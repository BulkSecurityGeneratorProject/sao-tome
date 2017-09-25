import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { Pedido } from './pedido.model';
import { PedidoService } from './pedido.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-pedido',
    templateUrl: './pedido.component.html'
})
export class PedidoComponent implements OnInit, OnDestroy {
pedidos: Pedido[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private pedidoService: PedidoService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.pedidoService.query().subscribe(
            (res: ResponseWrapper) => {
                this.pedidos = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPedidos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Pedido) {
        return item.id;
    }
    registerChangeInPedidos() {
        this.eventSubscriber = this.eventManager.subscribe('pedidoListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
