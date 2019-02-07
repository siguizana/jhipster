import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFraude } from 'app/shared/model/fraude.model';
import { FraudeService } from './fraude.service';

@Component({
    selector: 'jhi-fraude-delete-dialog',
    templateUrl: './fraude-delete-dialog.component.html'
})
export class FraudeDeleteDialogComponent {
    fraude: IFraude;

    constructor(protected fraudeService: FraudeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.fraudeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'fraudeListModification',
                content: 'Deleted an fraude'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-fraude-delete-popup',
    template: ''
})
export class FraudeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ fraude }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FraudeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.fraude = fraude;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/fraude', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/fraude', { outlets: { popup: null } }]);
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
