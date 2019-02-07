/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { CentreCompositionUpdateComponent } from 'app/entities/centre-composition/centre-composition-update.component';
import { CentreCompositionService } from 'app/entities/centre-composition/centre-composition.service';
import { CentreComposition } from 'app/shared/model/centre-composition.model';

describe('Component Tests', () => {
    describe('CentreComposition Management Update Component', () => {
        let comp: CentreCompositionUpdateComponent;
        let fixture: ComponentFixture<CentreCompositionUpdateComponent>;
        let service: CentreCompositionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [CentreCompositionUpdateComponent]
            })
                .overrideTemplate(CentreCompositionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CentreCompositionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CentreCompositionService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CentreComposition(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.centreComposition = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CentreComposition();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.centreComposition = entity;
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
