/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SigecTestModule } from '../../../test.module';
import { SalleCompositionDeleteDialogComponent } from 'app/entities/salle-composition/salle-composition-delete-dialog.component';
import { SalleCompositionService } from 'app/entities/salle-composition/salle-composition.service';

describe('Component Tests', () => {
    describe('SalleComposition Management Delete Component', () => {
        let comp: SalleCompositionDeleteDialogComponent;
        let fixture: ComponentFixture<SalleCompositionDeleteDialogComponent>;
        let service: SalleCompositionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [SalleCompositionDeleteDialogComponent]
            })
                .overrideTemplate(SalleCompositionDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SalleCompositionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SalleCompositionService);
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
