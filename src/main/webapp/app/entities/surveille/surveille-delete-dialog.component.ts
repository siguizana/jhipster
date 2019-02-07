import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISurveille } from 'app/shared/model/surveille.model';
import { SurveilleService } from './surveille.service';

@Component({
    selector: 'jhi-surveille-delete-dialog',
    templateUrl: './surveille-delete-dialog.component.html'
})
export class SurveilleDeleteDialogComponent {
    surveille: ISurveille;

    constructor(
        protected surveilleService: SurveilleService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.surveilleService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'surveilleListModification',
                content: 'Deleted an surveille'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-surveille-delete-popup',
    template: ''
})
export class SurveilleDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ surveille }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SurveilleDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.surveille = surveille;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/surveille', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/surveille', { outlets: { popup: null } }]);
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
