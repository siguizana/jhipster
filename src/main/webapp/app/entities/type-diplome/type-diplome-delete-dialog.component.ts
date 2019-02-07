import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeDiplome } from 'app/shared/model/type-diplome.model';
import { TypeDiplomeService } from './type-diplome.service';

@Component({
    selector: 'jhi-type-diplome-delete-dialog',
    templateUrl: './type-diplome-delete-dialog.component.html'
})
export class TypeDiplomeDeleteDialogComponent {
    typeDiplome: ITypeDiplome;

    constructor(
        protected typeDiplomeService: TypeDiplomeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeDiplomeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'typeDiplomeListModification',
                content: 'Deleted an typeDiplome'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-diplome-delete-popup',
    template: ''
})
export class TypeDiplomeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeDiplome }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TypeDiplomeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.typeDiplome = typeDiplome;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/type-diplome', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/type-diplome', { outlets: { popup: null } }]);
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
