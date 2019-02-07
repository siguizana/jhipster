import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICritereExamen } from 'app/shared/model/critere-examen.model';
import { CritereExamenService } from './critere-examen.service';

@Component({
    selector: 'jhi-critere-examen-delete-dialog',
    templateUrl: './critere-examen-delete-dialog.component.html'
})
export class CritereExamenDeleteDialogComponent {
    critereExamen: ICritereExamen;

    constructor(
        protected critereExamenService: CritereExamenService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.critereExamenService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'critereExamenListModification',
                content: 'Deleted an critereExamen'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-critere-examen-delete-popup',
    template: ''
})
export class CritereExamenDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ critereExamen }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CritereExamenDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.critereExamen = critereExamen;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/critere-examen', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/critere-examen', { outlets: { popup: null } }]);
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
