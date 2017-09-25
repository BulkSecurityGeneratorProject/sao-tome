import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Comanda } from './comanda.model';
import { ComandaPopupService } from './comanda-popup.service';
import { ComandaService } from './comanda.service';

@Component({
    selector: 'jhi-comanda-dialog',
    templateUrl: './comanda-dialog.component.html'
})
export class ComandaDialogComponent implements OnInit {

    comanda: Comanda;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private comandaService: ComandaService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.comanda.id !== undefined) {
            this.subscribeToSaveResponse(
                this.comandaService.update(this.comanda));
        } else {
            this.subscribeToSaveResponse(
                this.comandaService.create(this.comanda));
        }
    }

    private subscribeToSaveResponse(result: Observable<Comanda>) {
        result.subscribe((res: Comanda) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Comanda) {
        this.eventManager.broadcast({ name: 'comandaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-comanda-popup',
    template: ''
})
export class ComandaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private comandaPopupService: ComandaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.comandaPopupService
                    .open(ComandaDialogComponent as Component, params['id']);
            } else {
                this.comandaPopupService
                    .open(ComandaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
