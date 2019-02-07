import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IZoneExamen } from 'app/shared/model/zone-examen.model';
import { ZoneExamenService } from './zone-examen.service';

@Component({
    selector: 'jhi-zone-examen-delete-dialog',
    templateUrl: './zone-examen-delete-dialog.component.html'
})
export class ZoneExamenDeleteDialogComponent {
    zoneExamen: IZoneExamen;

    constructor(
        protected zoneExamenService: ZoneExamenService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.zoneExamenService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'zoneExamenListModification',
                content: 'Deleted an zoneExamen'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-zone-examen-delete-popup',
    template: ''
})
export class ZoneExamenDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ zoneExamen }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ZoneExamenDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.zoneExamen = zoneExamen;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/zone-examen', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/zone-examen', { outlets: { popup: null } }]);
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
