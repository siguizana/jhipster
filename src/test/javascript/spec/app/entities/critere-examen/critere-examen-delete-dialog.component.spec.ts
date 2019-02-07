/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SigecTestModule } from '../../../test.module';
import { CritereExamenDeleteDialogComponent } from 'app/entities/critere-examen/critere-examen-delete-dialog.component';
import { CritereExamenService } from 'app/entities/critere-examen/critere-examen.service';

describe('Component Tests', () => {
    describe('CritereExamen Management Delete Component', () => {
        let comp: CritereExamenDeleteDialogComponent;
        let fixture: ComponentFixture<CritereExamenDeleteDialogComponent>;
        let service: CritereExamenService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [CritereExamenDeleteDialogComponent]
            })
                .overrideTemplate(CritereExamenDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CritereExamenDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CritereExamenService);
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
