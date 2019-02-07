import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGroupeEpreuve } from 'app/shared/model/groupe-epreuve.model';
import { GroupeEpreuveService } from './groupe-epreuve.service';

@Component({
    selector: 'jhi-groupe-epreuve-delete-dialog',
    templateUrl: './groupe-epreuve-delete-dialog.component.html'
})
export class GroupeEpreuveDeleteDialogComponent {
    groupeEpreuve: IGroupeEpreuve;

    constructor(
        protected groupeEpreuveService: GroupeEpreuveService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.groupeEpreuveService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'groupeEpreuveListModification',
                content: 'Deleted an groupeEpreuve'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-groupe-epreuve-delete-popup',
    template: ''
})
export class GroupeEpreuveDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ groupeEpreuve }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(GroupeEpreuveDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.groupeEpreuve = groupeEpreuve;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/groupe-epreuve', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/groupe-epreuve', { outlets: { popup: null } }]);
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
