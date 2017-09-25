import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { Comanda } from './comanda.model';
import { ComandaService } from './comanda.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-comanda',
    templateUrl: './comanda.component.html'
})
export class ComandaComponent implements OnInit, OnDestroy {
comandas: Comanda[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private comandaService: ComandaService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.comandaService.query().subscribe(
            (res: ResponseWrapper) => {
                this.comandas = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInComandas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Comanda) {
        return item.id;
    }
    registerChangeInComandas() {
        this.eventSubscriber = this.eventManager.subscribe('comandaListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
