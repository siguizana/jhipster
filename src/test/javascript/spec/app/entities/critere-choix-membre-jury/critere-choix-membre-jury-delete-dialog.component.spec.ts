/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SigecTestModule } from '../../../test.module';
import { CritereChoixMembreJuryDeleteDialogComponent } from 'app/entities/critere-choix-membre-jury/critere-choix-membre-jury-delete-dialog.component';
import { CritereChoixMembreJuryService } from 'app/entities/critere-choix-membre-jury/critere-choix-membre-jury.service';

describe('Component Tests', () => {
    describe('CritereChoixMembreJury Management Delete Component', () => {
        let comp: CritereChoixMembreJuryDeleteDialogComponent;
        let fixture: ComponentFixture<CritereChoixMembreJuryDeleteDialogComponent>;
        let service: CritereChoixMembreJuryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [CritereChoixMembreJuryDeleteDialogComponent]
            })
                .overrideTemplate(CritereChoixMembreJuryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CritereChoixMembreJuryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CritereChoixMembreJuryService);
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
