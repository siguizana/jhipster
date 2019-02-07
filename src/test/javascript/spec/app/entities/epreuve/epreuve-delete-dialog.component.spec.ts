/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SigecTestModule } from '../../../test.module';
import { EpreuveDeleteDialogComponent } from 'app/entities/epreuve/epreuve-delete-dialog.component';
import { EpreuveService } from 'app/entities/epreuve/epreuve.service';

describe('Component Tests', () => {
    describe('Epreuve Management Delete Component', () => {
        let comp: EpreuveDeleteDialogComponent;
        let fixture: ComponentFixture<EpreuveDeleteDialogComponent>;
        let service: EpreuveService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [EpreuveDeleteDialogComponent]
            })
                .overrideTemplate(EpreuveDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EpreuveDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EpreuveService);
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
