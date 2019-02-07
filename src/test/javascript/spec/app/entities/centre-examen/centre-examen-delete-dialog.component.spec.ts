/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SigecTestModule } from '../../../test.module';
import { CentreExamenDeleteDialogComponent } from 'app/entities/centre-examen/centre-examen-delete-dialog.component';
import { CentreExamenService } from 'app/entities/centre-examen/centre-examen.service';

describe('Component Tests', () => {
    describe('CentreExamen Management Delete Component', () => {
        let comp: CentreExamenDeleteDialogComponent;
        let fixture: ComponentFixture<CentreExamenDeleteDialogComponent>;
        let service: CentreExamenService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [CentreExamenDeleteDialogComponent]
            })
                .overrideTemplate(CentreExamenDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CentreExamenDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CentreExamenService);
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
