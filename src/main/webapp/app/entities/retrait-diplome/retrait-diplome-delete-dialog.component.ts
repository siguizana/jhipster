import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRetraitDiplome } from 'app/shared/model/retrait-diplome.model';
import { RetraitDiplomeService } from './retrait-diplome.service';

@Component({
    selector: 'jhi-retrait-diplome-delete-dialog',
    templateUrl: './retrait-diplome-delete-dialog.component.html'
})
export class RetraitDiplomeDeleteDialogComponent {
    retraitDiplome: IRetraitDiplome;

    constructor(
        protected retraitDiplomeService: RetraitDiplomeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.retraitDiplomeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'retraitDiplomeListModification',
                content: 'Deleted an retraitDiplome'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-retrait-diplome-delete-popup',
    template: ''
})
export class RetraitDiplomeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ retraitDiplome }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RetraitDiplomeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.retraitDiplome = retraitDiplome;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/retrait-diplome', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/retrait-diplome', { outlets: { popup: null } }]);
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
