/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SigecTestModule } from '../../../test.module';
import { CebDeleteDialogComponent } from 'app/entities/ceb/ceb-delete-dialog.component';
import { CebService } from 'app/entities/ceb/ceb.service';

describe('Component Tests', () => {
    describe('Ceb Management Delete Component', () => {
        let comp: CebDeleteDialogComponent;
        let fixture: ComponentFixture<CebDeleteDialogComponent>;
        let service: CebService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [CebDeleteDialogComponent]
            })
                .overrideTemplate(CebDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CebDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CebService);
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
