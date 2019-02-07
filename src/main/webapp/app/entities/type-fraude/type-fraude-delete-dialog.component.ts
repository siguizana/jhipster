import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeFraude } from 'app/shared/model/type-fraude.model';
import { TypeFraudeService } from './type-fraude.service';

@Component({
    selector: 'jhi-type-fraude-delete-dialog',
    templateUrl: './type-fraude-delete-dialog.component.html'
})
export class TypeFraudeDeleteDialogComponent {
    typeFraude: ITypeFraude;

    constructor(
        protected typeFraudeService: TypeFraudeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeFraudeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'typeFraudeListModification',
                content: 'Deleted an typeFraude'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-fraude-delete-popup',
    template: ''
})
export class TypeFraudeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeFraude }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TypeFraudeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.typeFraude = typeFraude;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/type-fraude', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/type-fraude', { outlets: { popup: null } }]);
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
