import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Estoque } from './estoque.model';
import { EstoqueService } from './estoque.service';

@Component({
    selector: 'jhi-estoque-detail',
    templateUrl: './estoque-detail.component.html'
})
export class EstoqueDetailComponent implements OnInit, OnDestroy {

    estoque: Estoque;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private estoqueService: EstoqueService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEstoques();
    }

    load(id) {
        this.estoqueService.find(id).subscribe((estoque) => {
            this.estoque = estoque;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEstoques() {
        this.eventSubscriber = this.eventManager.subscribe(
            'estoqueListModification',
            (response) => this.load(this.estoque.id)
        );
    }
}
