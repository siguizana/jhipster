import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOptionConcoursRattache } from 'app/shared/model/option-concours-rattache.model';
import { OptionConcoursRattacheService } from './option-concours-rattache.service';

@Component({
    selector: 'jhi-option-concours-rattache-delete-dialog',
    templateUrl: './option-concours-rattache-delete-dialog.component.html'
})
export class OptionConcoursRattacheDeleteDialogComponent {
    optionConcoursRattache: IOptionConcoursRattache;

    constructor(
        protected optionConcoursRattacheService: OptionConcoursRattacheService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.optionConcoursRattacheService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'optionConcoursRattacheListModification',
                content: 'Deleted an optionConcoursRattache'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-option-concours-rattache-delete-popup',
    template: ''
})
export class OptionConcoursRattacheDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ optionConcoursRattache }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(OptionConcoursRattacheDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.optionConcoursRattache = optionConcoursRattache;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/option-concours-rattache', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/option-concours-rattache', { outlets: { popup: null } }]);
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
