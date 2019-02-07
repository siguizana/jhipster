/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SigecTestModule } from '../../../test.module';
import { EnseignantDeleteDialogComponent } from 'app/entities/enseignant/enseignant-delete-dialog.component';
import { EnseignantService } from 'app/entities/enseignant/enseignant.service';

describe('Component Tests', () => {
    describe('Enseignant Management Delete Component', () => {
        let comp: EnseignantDeleteDialogComponent;
        let fixture: ComponentFixture<EnseignantDeleteDialogComponent>;
        let service: EnseignantService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [EnseignantDeleteDialogComponent]
            })
                .overrideTemplate(EnseignantDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EnseignantDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EnseignantService);
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
