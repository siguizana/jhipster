/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SigecTestModule } from '../../../test.module';
import { EtapeExamenDeleteDialogComponent } from 'app/entities/etape-examen/etape-examen-delete-dialog.component';
import { EtapeExamenService } from 'app/entities/etape-examen/etape-examen.service';

describe('Component Tests', () => {
    describe('EtapeExamen Management Delete Component', () => {
        let comp: EtapeExamenDeleteDialogComponent;
        let fixture: ComponentFixture<EtapeExamenDeleteDialogComponent>;
        let service: EtapeExamenService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [EtapeExamenDeleteDialogComponent]
            })
                .overrideTemplate(EtapeExamenDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EtapeExamenDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EtapeExamenService);
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
