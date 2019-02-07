/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SigecTestModule } from '../../../test.module';
import { CompositionCopieDeleteDialogComponent } from 'app/entities/composition-copie/composition-copie-delete-dialog.component';
import { CompositionCopieService } from 'app/entities/composition-copie/composition-copie.service';

describe('Component Tests', () => {
    describe('CompositionCopie Management Delete Component', () => {
        let comp: CompositionCopieDeleteDialogComponent;
        let fixture: ComponentFixture<CompositionCopieDeleteDialogComponent>;
        let service: CompositionCopieService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [CompositionCopieDeleteDialogComponent]
            })
                .overrideTemplate(CompositionCopieDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CompositionCopieDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompositionCopieService);
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
