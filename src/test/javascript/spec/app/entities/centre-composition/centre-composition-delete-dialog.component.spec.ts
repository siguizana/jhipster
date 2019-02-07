/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SigecTestModule } from '../../../test.module';
import { CentreCompositionDeleteDialogComponent } from 'app/entities/centre-composition/centre-composition-delete-dialog.component';
import { CentreCompositionService } from 'app/entities/centre-composition/centre-composition.service';

describe('Component Tests', () => {
    describe('CentreComposition Management Delete Component', () => {
        let comp: CentreCompositionDeleteDialogComponent;
        let fixture: ComponentFixture<CentreCompositionDeleteDialogComponent>;
        let service: CentreCompositionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [CentreCompositionDeleteDialogComponent]
            })
                .overrideTemplate(CentreCompositionDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CentreCompositionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CentreCompositionService);
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
