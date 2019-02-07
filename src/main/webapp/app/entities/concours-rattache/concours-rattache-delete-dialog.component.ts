import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConcoursRattache } from 'app/shared/model/concours-rattache.model';
import { ConcoursRattacheService } from './concours-rattache.service';

@Component({
    selector: 'jhi-concours-rattache-delete-dialog',
    templateUrl: './concours-rattache-delete-dialog.component.html'
})
export class ConcoursRattacheDeleteDialogComponent {
    concoursRattache: IConcoursRattache;

    constructor(
        protected concoursRattacheService: ConcoursRattacheService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.concoursRattacheService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'concoursRattacheListModification',
                content: 'Deleted an concoursRattache'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-concours-rattache-delete-popup',
    template: ''
})
export class ConcoursRattacheDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ concoursRattache }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ConcoursRattacheDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.concoursRattache = concoursRattache;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/concours-rattache', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/concours-rattache', { outlets: { popup: null } }]);
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
