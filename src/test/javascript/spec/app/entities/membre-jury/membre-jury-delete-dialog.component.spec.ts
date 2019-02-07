/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SigecTestModule } from '../../../test.module';
import { MembreJuryDeleteDialogComponent } from 'app/entities/membre-jury/membre-jury-delete-dialog.component';
import { MembreJuryService } from 'app/entities/membre-jury/membre-jury.service';

describe('Component Tests', () => {
    describe('MembreJury Management Delete Component', () => {
        let comp: MembreJuryDeleteDialogComponent;
        let fixture: ComponentFixture<MembreJuryDeleteDialogComponent>;
        let service: MembreJuryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [MembreJuryDeleteDialogComponent]
            })
                .overrideTemplate(MembreJuryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MembreJuryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MembreJuryService);
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
