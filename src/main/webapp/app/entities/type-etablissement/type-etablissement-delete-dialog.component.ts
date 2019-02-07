import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeEtablissement } from 'app/shared/model/type-etablissement.model';
import { TypeEtablissementService } from './type-etablissement.service';

@Component({
    selector: 'jhi-type-etablissement-delete-dialog',
    templateUrl: './type-etablissement-delete-dialog.component.html'
})
export class TypeEtablissementDeleteDialogComponent {
    typeEtablissement: ITypeEtablissement;

    constructor(
        protected typeEtablissementService: TypeEtablissementService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeEtablissementService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'typeEtablissementListModification',
                content: 'Deleted an typeEtablissement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-etablissement-delete-popup',
    template: ''
})
export class TypeEtablissementDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeEtablissement }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TypeEtablissementDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.typeEtablissement = typeEtablissement;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/type-etablissement', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/type-etablissement', { outlets: { popup: null } }]);
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
