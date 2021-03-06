import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Municipio } from './municipio.model';
import { MunicipioPopupService } from './municipio-popup.service';
import { MunicipioService } from './municipio.service';

@Component({
    selector: 'jhi-municipio-dialog',
    templateUrl: './municipio-dialog.component.html'
})
export class MunicipioDialogComponent implements OnInit {

    municipio: Municipio;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private municipioService: MunicipioService,
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
        if (this.municipio.id !== undefined) {
            this.subscribeToSaveResponse(
                this.municipioService.update(this.municipio));
        } else {
            this.subscribeToSaveResponse(
                this.municipioService.create(this.municipio));
        }
    }

    private subscribeToSaveResponse(result: Observable<Municipio>) {
        result.subscribe((res: Municipio) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Municipio) {
        this.eventManager.broadcast({ name: 'municipioListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-municipio-popup',
    template: ''
})
export class MunicipioPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private municipioPopupService: MunicipioPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.municipioPopupService
                    .open(MunicipioDialogComponent as Component, params['id']);
            } else {
                this.municipioPopupService
                    .open(MunicipioDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
