/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SigecTestModule } from '../../../test.module';
import { RetraitDiplomeDeleteDialogComponent } from 'app/entities/retrait-diplome/retrait-diplome-delete-dialog.component';
import { RetraitDiplomeService } from 'app/entities/retrait-diplome/retrait-diplome.service';

describe('Component Tests', () => {
    describe('RetraitDiplome Management Delete Component', () => {
        let comp: RetraitDiplomeDeleteDialogComponent;
        let fixture: ComponentFixture<RetraitDiplomeDeleteDialogComponent>;
        let service: RetraitDiplomeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [RetraitDiplomeDeleteDialogComponent]
            })
                .overrideTemplate(RetraitDiplomeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RetraitDiplomeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RetraitDiplomeService);
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
