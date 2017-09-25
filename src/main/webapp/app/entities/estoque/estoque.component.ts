import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { Estoque } from './estoque.model';
import { EstoqueService } from './estoque.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-estoque',
    templateUrl: './estoque.component.html'
})
export class EstoqueComponent implements OnInit, OnDestroy {
estoques: Estoque[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private estoqueService: EstoqueService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.estoqueService.query().subscribe(
            (res: ResponseWrapper) => {
                this.estoques = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEstoques();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Estoque) {
        return item.id;
    }
    registerChangeInEstoques() {
        this.eventSubscriber = this.eventManager.subscribe('estoqueListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
