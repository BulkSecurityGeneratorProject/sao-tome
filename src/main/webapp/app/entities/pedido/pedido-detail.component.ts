import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Pedido } from './pedido.model';
import { PedidoService } from './pedido.service';

@Component({
    selector: 'jhi-pedido-detail',
    templateUrl: './pedido-detail.component.html'
})
export class PedidoDetailComponent implements OnInit, OnDestroy {

    pedido: Pedido;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private pedidoService: PedidoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPedidos();
    }

    load(id) {
        this.pedidoService.find(id).subscribe((pedido) => {
            this.pedido = pedido;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPedidos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pedidoListModification',
            (response) => this.load(this.pedido.id)
        );
    }
}
