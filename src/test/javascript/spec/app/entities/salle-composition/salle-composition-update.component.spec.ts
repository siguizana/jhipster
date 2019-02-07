/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { SalleCompositionUpdateComponent } from 'app/entities/salle-composition/salle-composition-update.component';
import { SalleCompositionService } from 'app/entities/salle-composition/salle-composition.service';
import { SalleComposition } from 'app/shared/model/salle-composition.model';

describe('Component Tests', () => {
    describe('SalleComposition Management Update Component', () => {
        let comp: SalleCompositionUpdateComponent;
        let fixture: ComponentFixture<SalleCompositionUpdateComponent>;
        let service: SalleCompositionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [SalleCompositionUpdateComponent]
            })
                .overrideTemplate(SalleCompositionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SalleCompositionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SalleCompositionService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new SalleComposition(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.salleComposition = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new SalleComposition();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.salleComposition = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
