/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SigecTestModule } from '../../../test.module';
import { GroupeEpreuveDeleteDialogComponent } from 'app/entities/groupe-epreuve/groupe-epreuve-delete-dialog.component';
import { GroupeEpreuveService } from 'app/entities/groupe-epreuve/groupe-epreuve.service';

describe('Component Tests', () => {
    describe('GroupeEpreuve Management Delete Component', () => {
        let comp: GroupeEpreuveDeleteDialogComponent;
        let fixture: ComponentFixture<GroupeEpreuveDeleteDialogComponent>;
        let service: GroupeEpreuveService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [GroupeEpreuveDeleteDialogComponent]
            })
                .overrideTemplate(GroupeEpreuveDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GroupeEpreuveDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GroupeEpreuveService);
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
