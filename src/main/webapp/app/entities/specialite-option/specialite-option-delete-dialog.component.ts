import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISpecialiteOption } from 'app/shared/model/specialite-option.model';
import { SpecialiteOptionService } from './specialite-option.service';

@Component({
    selector: 'jhi-specialite-option-delete-dialog',
    templateUrl: './specialite-option-delete-dialog.component.html'
})
export class SpecialiteOptionDeleteDialogComponent {
    specialiteOption: ISpecialiteOption;

    constructor(
        protected specialiteOptionService: SpecialiteOptionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.specialiteOptionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'specialiteOptionListModification',
                content: 'Deleted an specialiteOption'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-specialite-option-delete-popup',
    template: ''
})
export class SpecialiteOptionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ specialiteOption }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SpecialiteOptionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.specialiteOption = specialiteOption;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/specialite-option', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/specialite-option', { outlets: { popup: null } }]);
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
