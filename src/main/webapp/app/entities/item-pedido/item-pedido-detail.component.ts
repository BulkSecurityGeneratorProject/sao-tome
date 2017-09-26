import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ItemPedido } from './item-pedido.model';
import { ItemPedidoService } from './item-pedido.service';

@Component({
    selector: 'jhi-item-pedido-detail',
    templateUrl: './item-pedido-detail.component.html'
})
export class ItemPedidoDetailComponent implements OnInit, OnDestroy {

    itemPedido: ItemPedido;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private itemPedidoService: ItemPedidoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInItemPedidos();
    }

    load(id) {
        this.itemPedidoService.find(id).subscribe((itemPedido) => {
            this.itemPedido = itemPedido;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInItemPedidos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'itemPedidoListModification',
            (response) => this.load(this.itemPedido.id)
        );
    }
}
