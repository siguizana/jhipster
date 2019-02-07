/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SigecTestModule } from '../../../test.module';
import { OptionConcoursRattacheDeleteDialogComponent } from 'app/entities/option-concours-rattache/option-concours-rattache-delete-dialog.component';
import { OptionConcoursRattacheService } from 'app/entities/option-concours-rattache/option-concours-rattache.service';

describe('Component Tests', () => {
    describe('OptionConcoursRattache Management Delete Component', () => {
        let comp: OptionConcoursRattacheDeleteDialogComponent;
        let fixture: ComponentFixture<OptionConcoursRattacheDeleteDialogComponent>;
        let service: OptionConcoursRattacheService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [OptionConcoursRattacheDeleteDialogComponent]
            })
                .overrideTemplate(OptionConcoursRattacheDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OptionConcoursRattacheDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OptionConcoursRattacheService);
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
