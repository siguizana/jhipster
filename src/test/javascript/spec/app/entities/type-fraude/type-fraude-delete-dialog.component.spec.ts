/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SigecTestModule } from '../../../test.module';
import { TypeFraudeDeleteDialogComponent } from 'app/entities/type-fraude/type-fraude-delete-dialog.component';
import { TypeFraudeService } from 'app/entities/type-fraude/type-fraude.service';

describe('Component Tests', () => {
    describe('TypeFraude Management Delete Component', () => {
        let comp: TypeFraudeDeleteDialogComponent;
        let fixture: ComponentFixture<TypeFraudeDeleteDialogComponent>;
        let service: TypeFraudeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [TypeFraudeDeleteDialogComponent]
            })
                .overrideTemplate(TypeFraudeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeFraudeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeFraudeService);
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
