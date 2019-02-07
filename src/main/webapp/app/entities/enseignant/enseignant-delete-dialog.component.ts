import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEnseignant } from 'app/shared/model/enseignant.model';
import { EnseignantService } from './enseignant.service';

@Component({
    selector: 'jhi-enseignant-delete-dialog',
    templateUrl: './enseignant-delete-dialog.component.html'
})
export class EnseignantDeleteDialogComponent {
    enseignant: IEnseignant;

    constructor(
        protected enseignantService: EnseignantService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.enseignantService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'enseignantListModification',
                content: 'Deleted an enseignant'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-enseignant-delete-popup',
    template: ''
})
export class EnseignantDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ enseignant }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EnseignantDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.enseignant = enseignant;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/enseignant', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/enseignant', { outlets: { popup: null } }]);
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
