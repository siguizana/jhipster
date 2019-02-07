/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SigecTestModule } from '../../../test.module';
import { TypeEpreuveDeleteDialogComponent } from 'app/entities/type-epreuve/type-epreuve-delete-dialog.component';
import { TypeEpreuveService } from 'app/entities/type-epreuve/type-epreuve.service';

describe('Component Tests', () => {
    describe('TypeEpreuve Management Delete Component', () => {
        let comp: TypeEpreuveDeleteDialogComponent;
        let fixture: ComponentFixture<TypeEpreuveDeleteDialogComponent>;
        let service: TypeEpreuveService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [TypeEpreuveDeleteDialogComponent]
            })
                .overrideTemplate(TypeEpreuveDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeEpreuveDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeEpreuveService);
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
