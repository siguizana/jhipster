import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeMembreJury } from 'app/shared/model/type-membre-jury.model';
import { TypeMembreJuryService } from './type-membre-jury.service';

@Component({
    selector: 'jhi-type-membre-jury-delete-dialog',
    templateUrl: './type-membre-jury-delete-dialog.component.html'
})
export class TypeMembreJuryDeleteDialogComponent {
    typeMembreJury: ITypeMembreJury;

    constructor(
        protected typeMembreJuryService: TypeMembreJuryService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeMembreJuryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'typeMembreJuryListModification',
                content: 'Deleted an typeMembreJury'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-membre-jury-delete-popup',
    template: ''
})
export class TypeMembreJuryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeMembreJury }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TypeMembreJuryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.typeMembreJury = typeMembreJury;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/type-membre-jury', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/type-membre-jury', { outlets: { popup: null } }]);
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
