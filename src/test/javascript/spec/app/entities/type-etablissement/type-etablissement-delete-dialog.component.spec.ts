/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SigecTestModule } from '../../../test.module';
import { TypeEtablissementDeleteDialogComponent } from 'app/entities/type-etablissement/type-etablissement-delete-dialog.component';
import { TypeEtablissementService } from 'app/entities/type-etablissement/type-etablissement.service';

describe('Component Tests', () => {
    describe('TypeEtablissement Management Delete Component', () => {
        let comp: TypeEtablissementDeleteDialogComponent;
        let fixture: ComponentFixture<TypeEtablissementDeleteDialogComponent>;
        let service: TypeEtablissementService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [TypeEtablissementDeleteDialogComponent]
            })
                .overrideTemplate(TypeEtablissementDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeEtablissementDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeEtablissementService);
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
