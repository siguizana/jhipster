import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMembreJuryJury } from 'app/shared/model/membre-jury-jury.model';
import { MembreJuryJuryService } from './membre-jury-jury.service';

@Component({
    selector: 'jhi-membre-jury-jury-delete-dialog',
    templateUrl: './membre-jury-jury-delete-dialog.component.html'
})
export class MembreJuryJuryDeleteDialogComponent {
    membreJuryJury: IMembreJuryJury;

    constructor(
        protected membreJuryJuryService: MembreJuryJuryService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.membreJuryJuryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'membreJuryJuryListModification',
                content: 'Deleted an membreJuryJury'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-membre-jury-jury-delete-popup',
    template: ''
})
export class MembreJuryJuryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ membreJuryJury }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MembreJuryJuryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.membreJuryJury = membreJuryJury;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/membre-jury-jury', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/membre-jury-jury', { outlets: { popup: null } }]);
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
