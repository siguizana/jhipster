/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SigecTestModule } from '../../../test.module';
import { EpreuveSpecialiteOptionDeleteDialogComponent } from 'app/entities/epreuve-specialite-option/epreuve-specialite-option-delete-dialog.component';
import { EpreuveSpecialiteOptionService } from 'app/entities/epreuve-specialite-option/epreuve-specialite-option.service';

describe('Component Tests', () => {
    describe('EpreuveSpecialiteOption Management Delete Component', () => {
        let comp: EpreuveSpecialiteOptionDeleteDialogComponent;
        let fixture: ComponentFixture<EpreuveSpecialiteOptionDeleteDialogComponent>;
        let service: EpreuveSpecialiteOptionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [EpreuveSpecialiteOptionDeleteDialogComponent]
            })
                .overrideTemplate(EpreuveSpecialiteOptionDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EpreuveSpecialiteOptionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EpreuveSpecialiteOptionService);
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
