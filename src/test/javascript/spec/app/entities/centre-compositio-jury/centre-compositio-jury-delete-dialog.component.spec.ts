/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SigecTestModule } from '../../../test.module';
import { CentreCompositioJuryDeleteDialogComponent } from 'app/entities/centre-compositio-jury/centre-compositio-jury-delete-dialog.component';
import { CentreCompositioJuryService } from 'app/entities/centre-compositio-jury/centre-compositio-jury.service';

describe('Component Tests', () => {
    describe('CentreCompositioJury Management Delete Component', () => {
        let comp: CentreCompositioJuryDeleteDialogComponent;
        let fixture: ComponentFixture<CentreCompositioJuryDeleteDialogComponent>;
        let service: CentreCompositioJuryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [CentreCompositioJuryDeleteDialogComponent]
            })
                .overrideTemplate(CentreCompositioJuryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CentreCompositioJuryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CentreCompositioJuryService);
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
