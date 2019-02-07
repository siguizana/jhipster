import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICritereChoixMembreJury } from 'app/shared/model/critere-choix-membre-jury.model';
import { CritereChoixMembreJuryService } from './critere-choix-membre-jury.service';

@Component({
    selector: 'jhi-critere-choix-membre-jury-delete-dialog',
    templateUrl: './critere-choix-membre-jury-delete-dialog.component.html'
})
export class CritereChoixMembreJuryDeleteDialogComponent {
    critereChoixMembreJury: ICritereChoixMembreJury;

    constructor(
        protected critereChoixMembreJuryService: CritereChoixMembreJuryService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.critereChoixMembreJuryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'critereChoixMembreJuryListModification',
                content: 'Deleted an critereChoixMembreJury'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-critere-choix-membre-jury-delete-popup',
    template: ''
})
export class CritereChoixMembreJuryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ critereChoixMembreJury }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CritereChoixMembreJuryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.critereChoixMembreJury = critereChoixMembreJury;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/critere-choix-membre-jury', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/critere-choix-membre-jury', { outlets: { popup: null } }]);
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
