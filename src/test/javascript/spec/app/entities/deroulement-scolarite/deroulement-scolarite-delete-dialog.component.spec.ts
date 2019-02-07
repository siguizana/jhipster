/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SigecTestModule } from '../../../test.module';
import { DeroulementScolariteDeleteDialogComponent } from 'app/entities/deroulement-scolarite/deroulement-scolarite-delete-dialog.component';
import { DeroulementScolariteService } from 'app/entities/deroulement-scolarite/deroulement-scolarite.service';

describe('Component Tests', () => {
    describe('DeroulementScolarite Management Delete Component', () => {
        let comp: DeroulementScolariteDeleteDialogComponent;
        let fixture: ComponentFixture<DeroulementScolariteDeleteDialogComponent>;
        let service: DeroulementScolariteService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [DeroulementScolariteDeleteDialogComponent]
            })
                .overrideTemplate(DeroulementScolariteDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DeroulementScolariteDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeroulementScolariteService);
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
