/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { EtapeExamenUpdateComponent } from 'app/entities/etape-examen/etape-examen-update.component';
import { EtapeExamenService } from 'app/entities/etape-examen/etape-examen.service';
import { EtapeExamen } from 'app/shared/model/etape-examen.model';

describe('Component Tests', () => {
    describe('EtapeExamen Management Update Component', () => {
        let comp: EtapeExamenUpdateComponent;
        let fixture: ComponentFixture<EtapeExamenUpdateComponent>;
        let service: EtapeExamenService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [EtapeExamenUpdateComponent]
            })
                .overrideTemplate(EtapeExamenUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EtapeExamenUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EtapeExamenService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new EtapeExamen(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.etapeExamen = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new EtapeExamen();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.etapeExamen = entity;
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
