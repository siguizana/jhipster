/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SigecTestModule } from '../../../test.module';
import { MembreJuryJuryDeleteDialogComponent } from 'app/entities/membre-jury-jury/membre-jury-jury-delete-dialog.component';
import { MembreJuryJuryService } from 'app/entities/membre-jury-jury/membre-jury-jury.service';

describe('Component Tests', () => {
    describe('MembreJuryJury Management Delete Component', () => {
        let comp: MembreJuryJuryDeleteDialogComponent;
        let fixture: ComponentFixture<MembreJuryJuryDeleteDialogComponent>;
        let service: MembreJuryJuryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [MembreJuryJuryDeleteDialogComponent]
            })
                .overrideTemplate(MembreJuryJuryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MembreJuryJuryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MembreJuryJuryService);
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
