/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { CritereExamenUpdateComponent } from 'app/entities/critere-examen/critere-examen-update.component';
import { CritereExamenService } from 'app/entities/critere-examen/critere-examen.service';
import { CritereExamen } from 'app/shared/model/critere-examen.model';

describe('Component Tests', () => {
    describe('CritereExamen Management Update Component', () => {
        let comp: CritereExamenUpdateComponent;
        let fixture: ComponentFixture<CritereExamenUpdateComponent>;
        let service: CritereExamenService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [CritereExamenUpdateComponent]
            })
                .overrideTemplate(CritereExamenUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CritereExamenUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CritereExamenService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CritereExamen(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.critereExamen = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CritereExamen();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.critereExamen = entity;
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
