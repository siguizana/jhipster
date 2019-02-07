/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SigecTestModule } from '../../../test.module';
import { PieceAConvictionDeleteDialogComponent } from 'app/entities/piece-a-conviction/piece-a-conviction-delete-dialog.component';
import { PieceAConvictionService } from 'app/entities/piece-a-conviction/piece-a-conviction.service';

describe('Component Tests', () => {
    describe('PieceAConviction Management Delete Component', () => {
        let comp: PieceAConvictionDeleteDialogComponent;
        let fixture: ComponentFixture<PieceAConvictionDeleteDialogComponent>;
        let service: PieceAConvictionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [PieceAConvictionDeleteDialogComponent]
            })
                .overrideTemplate(PieceAConvictionDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PieceAConvictionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PieceAConvictionService);
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
