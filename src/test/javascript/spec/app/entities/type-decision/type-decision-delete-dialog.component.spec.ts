/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SigecTestModule } from '../../../test.module';
import { TypeDecisionDeleteDialogComponent } from 'app/entities/type-decision/type-decision-delete-dialog.component';
import { TypeDecisionService } from 'app/entities/type-decision/type-decision.service';

describe('Component Tests', () => {
    describe('TypeDecision Management Delete Component', () => {
        let comp: TypeDecisionDeleteDialogComponent;
        let fixture: ComponentFixture<TypeDecisionDeleteDialogComponent>;
        let service: TypeDecisionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [TypeDecisionDeleteDialogComponent]
            })
                .overrideTemplate(TypeDecisionDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeDecisionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeDecisionService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
