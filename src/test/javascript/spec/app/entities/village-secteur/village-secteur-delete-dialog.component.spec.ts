/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SigecTestModule } from '../../../test.module';
import { VillageSecteurDeleteDialogComponent } from 'app/entities/village-secteur/village-secteur-delete-dialog.component';
import { VillageSecteurService } from 'app/entities/village-secteur/village-secteur.service';

describe('Component Tests', () => {
    describe('VillageSecteur Management Delete Component', () => {
        let comp: VillageSecteurDeleteDialogComponent;
        let fixture: ComponentFixture<VillageSecteurDeleteDialogComponent>;
        let service: VillageSecteurService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [VillageSecteurDeleteDialogComponent]
            })
                .overrideTemplate(VillageSecteurDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VillageSecteurDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VillageSecteurService);
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
