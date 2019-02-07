import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEpreuve } from 'app/shared/model/epreuve.model';
import { EpreuveService } from './epreuve.service';

@Component({
    selector: 'jhi-epreuve-delete-dialog',
    templateUrl: './epreuve-delete-dialog.component.html'
})
export class EpreuveDeleteDialogComponent {
    epreuve: IEpreuve;

    constructor(protected epreuveService: EpreuveService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.epreuveService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'epreuveListModification',
                content: 'Deleted an epreuve'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-epreuve-delete-popup',
    template: ''
})
export class EpreuveDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ epreuve }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EpreuveDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.epreuve = epreuve;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/epreuve', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/epreuve', { outlets: { popup: null } }]);
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
