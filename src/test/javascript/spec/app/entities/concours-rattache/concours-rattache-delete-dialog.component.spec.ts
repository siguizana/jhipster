/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SigecTestModule } from '../../../test.module';
import { ConcoursRattacheDeleteDialogComponent } from 'app/entities/concours-rattache/concours-rattache-delete-dialog.component';
import { ConcoursRattacheService } from 'app/entities/concours-rattache/concours-rattache.service';

describe('Component Tests', () => {
    describe('ConcoursRattache Management Delete Component', () => {
        let comp: ConcoursRattacheDeleteDialogComponent;
        let fixture: ComponentFixture<ConcoursRattacheDeleteDialogComponent>;
        let service: ConcoursRattacheService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [ConcoursRattacheDeleteDialogComponent]
            })
                .overrideTemplate(ConcoursRattacheDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ConcoursRattacheDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConcoursRattacheService);
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
