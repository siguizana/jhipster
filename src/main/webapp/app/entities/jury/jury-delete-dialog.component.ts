import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJury } from 'app/shared/model/jury.model';
import { JuryService } from './jury.service';

@Component({
    selector: 'jhi-jury-delete-dialog',
    templateUrl: './jury-delete-dialog.component.html'
})
export class JuryDeleteDialogComponent {
    jury: IJury;

    constructor(protected juryService: JuryService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.juryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'juryListModification',
                content: 'Deleted an jury'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-jury-delete-popup',
    template: ''
})
export class JuryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ jury }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(JuryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.jury = jury;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/jury', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/jury', { outlets: { popup: null } }]);
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
