/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { CentreExamenUpdateComponent } from 'app/entities/centre-examen/centre-examen-update.component';
import { CentreExamenService } from 'app/entities/centre-examen/centre-examen.service';
import { CentreExamen } from 'app/shared/model/centre-examen.model';

describe('Component Tests', () => {
    describe('CentreExamen Management Update Component', () => {
        let comp: CentreExamenUpdateComponent;
        let fixture: ComponentFixture<CentreExamenUpdateComponent>;
        let service: CentreExamenService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [CentreExamenUpdateComponent]
            })
                .overrideTemplate(CentreExamenUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CentreExamenUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CentreExamenService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CentreExamen(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.centreExamen = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CentreExamen();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.centreExamen = entity;
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
