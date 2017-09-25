import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Estoque } from './estoque.model';
import { EstoquePopupService } from './estoque-popup.service';
import { EstoqueService } from './estoque.service';
import { Produto, ProdutoService } from '../produto';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-estoque-dialog',
    templateUrl: './estoque-dialog.component.html'
})
export class EstoqueDialogComponent implements OnInit {

    estoque: Estoque;
    isSaving: boolean;

    produtos: Produto[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private estoqueService: EstoqueService,
        private produtoService: ProdutoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.produtoService
            .query({filter: 'estoque-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.estoque.produto || !this.estoque.produto.id) {
                    this.produtos = res.json;
                } else {
                    this.produtoService
                        .find(this.estoque.produto.id)
                        .subscribe((subRes: Produto) => {
                            this.produtos = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.estoque.id !== undefined) {
            this.subscribeToSaveResponse(
                this.estoqueService.update(this.estoque));
        } else {
            this.subscribeToSaveResponse(
                this.estoqueService.create(this.estoque));
        }
    }

    private subscribeToSaveResponse(result: Observable<Estoque>) {
        result.subscribe((res: Estoque) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Estoque) {
        this.eventManager.broadcast({ name: 'estoqueListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackProdutoById(index: number, item: Produto) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-estoque-popup',
    template: ''
})
export class EstoquePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private estoquePopupService: EstoquePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.estoquePopupService
                    .open(EstoqueDialogComponent as Component, params['id']);
            } else {
                this.estoquePopupService
                    .open(EstoqueDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
