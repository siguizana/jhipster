/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SigecTestModule } from '../../../test.module';
import { TypeCritereDeleteDialogComponent } from 'app/entities/type-critere/type-critere-delete-dialog.component';
import { TypeCritereService } from 'app/entities/type-critere/type-critere.service';

describe('Component Tests', () => {
    describe('TypeCritere Management Delete Component', () => {
        let comp: TypeCritereDeleteDialogComponent;
        let fixture: ComponentFixture<TypeCritereDeleteDialogComponent>;
        let service: TypeCritereService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [TypeCritereDeleteDialogComponent]
            })
                .overrideTemplate(TypeCritereDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeCritereDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeCritereService);
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
