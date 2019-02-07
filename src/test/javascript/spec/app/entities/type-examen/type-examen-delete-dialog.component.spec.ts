/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SigecTestModule } from '../../../test.module';
import { TypeExamenDeleteDialogComponent } from 'app/entities/type-examen/type-examen-delete-dialog.component';
import { TypeExamenService } from 'app/entities/type-examen/type-examen.service';

describe('Component Tests', () => {
    describe('TypeExamen Management Delete Component', () => {
        let comp: TypeExamenDeleteDialogComponent;
        let fixture: ComponentFixture<TypeExamenDeleteDialogComponent>;
        let service: TypeExamenService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [TypeExamenDeleteDialogComponent]
            })
                .overrideTemplate(TypeExamenDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeExamenDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeExamenService);
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
