/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SigecTestModule } from '../../../test.module';
import { HandicapeDeleteDialogComponent } from 'app/entities/handicape/handicape-delete-dialog.component';
import { HandicapeService } from 'app/entities/handicape/handicape.service';

describe('Component Tests', () => {
    describe('Handicape Management Delete Component', () => {
        let comp: HandicapeDeleteDialogComponent;
        let fixture: ComponentFixture<HandicapeDeleteDialogComponent>;
        let service: HandicapeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [HandicapeDeleteDialogComponent]
            })
                .overrideTemplate(HandicapeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HandicapeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HandicapeService);
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
