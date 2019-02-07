import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMention } from 'app/shared/model/mention.model';
import { MentionService } from './mention.service';

@Component({
    selector: 'jhi-mention-delete-dialog',
    templateUrl: './mention-delete-dialog.component.html'
})
export class MentionDeleteDialogComponent {
    mention: IMention;

    constructor(protected mentionService: MentionService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mentionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'mentionListModification',
                content: 'Deleted an mention'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mention-delete-popup',
    template: ''
})
export class MentionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ mention }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MentionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.mention = mention;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/mention', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/mention', { outlets: { popup: null } }]);
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
