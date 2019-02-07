import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEpreuveSpecialiteOption } from 'app/shared/model/epreuve-specialite-option.model';
import { EpreuveSpecialiteOptionService } from './epreuve-specialite-option.service';

@Component({
    selector: 'jhi-epreuve-specialite-option-delete-dialog',
    templateUrl: './epreuve-specialite-option-delete-dialog.component.html'
})
export class EpreuveSpecialiteOptionDeleteDialogComponent {
    epreuveSpecialiteOption: IEpreuveSpecialiteOption;

    constructor(
        protected epreuveSpecialiteOptionService: EpreuveSpecialiteOptionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.epreuveSpecialiteOptionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'epreuveSpecialiteOptionListModification',
                content: 'Deleted an epreuveSpecialiteOption'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-epreuve-specialite-option-delete-popup',
    template: ''
})
export class EpreuveSpecialiteOptionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ epreuveSpecialiteOption }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EpreuveSpecialiteOptionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.epreuveSpecialiteOption = epreuveSpecialiteOption;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/epreuve-specialite-option', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/epreuve-specialite-option', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
