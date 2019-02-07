import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeExamen } from 'app/shared/model/type-examen.model';
import { TypeExamenService } from './type-examen.service';

@Component({
    selector: 'jhi-type-examen-delete-dialog',
    templateUrl: './type-examen-delete-dialog.component.html'
})
export class TypeExamenDeleteDialogComponent {
    typeExamen: ITypeExamen;

    constructor(
        protected typeExamenService: TypeExamenService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeExamenService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'typeExamenListModification',
                content: 'Deleted an typeExamen'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-examen-delete-popup',
    template: ''
})
export class TypeExamenDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeExamen }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TypeExamenDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.typeExamen = typeExamen;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/type-examen', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/type-examen', { outlets: { popup: null } }]);
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
