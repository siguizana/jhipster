import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IChoixEtablissement } from 'app/shared/model/choix-etablissement.model';
import { ChoixEtablissementService } from './choix-etablissement.service';

@Component({
    selector: 'jhi-choix-etablissement-delete-dialog',
    templateUrl: './choix-etablissement-delete-dialog.component.html'
})
export class ChoixEtablissementDeleteDialogComponent {
    choixEtablissement: IChoixEtablissement;

    constructor(
        protected choixEtablissementService: ChoixEtablissementService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.choixEtablissementService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'choixEtablissementListModification',
                content: 'Deleted an choixEtablissement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-choix-etablissement-delete-popup',
    template: ''
})
export class ChoixEtablissementDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ choixEtablissement }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ChoixEtablissementDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.choixEtablissement = choixEtablissement;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/choix-etablissement', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/choix-etablissement', { outlets: { popup: null } }]);
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
