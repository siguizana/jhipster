/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SigecTestModule } from '../../../test.module';
import { TypeMembreJuryDeleteDialogComponent } from 'app/entities/type-membre-jury/type-membre-jury-delete-dialog.component';
import { TypeMembreJuryService } from 'app/entities/type-membre-jury/type-membre-jury.service';

describe('Component Tests', () => {
    describe('TypeMembreJury Management Delete Component', () => {
        let comp: TypeMembreJuryDeleteDialogComponent;
        let fixture: ComponentFixture<TypeMembreJuryDeleteDialogComponent>;
        let service: TypeMembreJuryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [TypeMembreJuryDeleteDialogComponent]
            })
                .overrideTemplate(TypeMembreJuryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeMembreJuryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeMembreJuryService);
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
