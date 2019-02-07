import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHandicape } from 'app/shared/model/handicape.model';
import { HandicapeService } from './handicape.service';

@Component({
    selector: 'jhi-handicape-delete-dialog',
    templateUrl: './handicape-delete-dialog.component.html'
})
export class HandicapeDeleteDialogComponent {
    handicape: IHandicape;

    constructor(
        protected handicapeService: HandicapeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.handicapeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'handicapeListModification',
                content: 'Deleted an handicape'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-handicape-delete-popup',
    template: ''
})
export class HandicapeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ handicape }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(HandicapeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.handicape = handicape;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/handicape', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/handicape', { outlets: { popup: null } }]);
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
