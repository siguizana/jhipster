import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVillageSecteur } from 'app/shared/model/village-secteur.model';
import { VillageSecteurService } from './village-secteur.service';

@Component({
    selector: 'jhi-village-secteur-delete-dialog',
    templateUrl: './village-secteur-delete-dialog.component.html'
})
export class VillageSecteurDeleteDialogComponent {
    villageSecteur: IVillageSecteur;

    constructor(
        protected villageSecteurService: VillageSecteurService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.villageSecteurService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'villageSecteurListModification',
                content: 'Deleted an villageSecteur'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-village-secteur-delete-popup',
    template: ''
})
export class VillageSecteurDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ villageSecteur }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(VillageSecteurDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.villageSecteur = villageSecteur;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/village-secteur', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/village-secteur', { outlets: { popup: null } }]);
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
