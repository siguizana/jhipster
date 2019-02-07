/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SigecTestModule } from '../../../test.module';
import { ChoixEtablissementDeleteDialogComponent } from 'app/entities/choix-etablissement/choix-etablissement-delete-dialog.component';
import { ChoixEtablissementService } from 'app/entities/choix-etablissement/choix-etablissement.service';

describe('Component Tests', () => {
    describe('ChoixEtablissement Management Delete Component', () => {
        let comp: ChoixEtablissementDeleteDialogComponent;
        let fixture: ComponentFixture<ChoixEtablissementDeleteDialogComponent>;
        let service: ChoixEtablissementService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [ChoixEtablissementDeleteDialogComponent]
            })
                .overrideTemplate(ChoixEtablissementDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ChoixEtablissementDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChoixEtablissementService);
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
