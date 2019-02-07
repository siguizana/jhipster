/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { ZoneExamenUpdateComponent } from 'app/entities/zone-examen/zone-examen-update.component';
import { ZoneExamenService } from 'app/entities/zone-examen/zone-examen.service';
import { ZoneExamen } from 'app/shared/model/zone-examen.model';

describe('Component Tests', () => {
    describe('ZoneExamen Management Update Component', () => {
        let comp: ZoneExamenUpdateComponent;
        let fixture: ComponentFixture<ZoneExamenUpdateComponent>;
        let service: ZoneExamenService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [ZoneExamenUpdateComponent]
            })
                .overrideTemplate(ZoneExamenUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ZoneExamenUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ZoneExamenService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ZoneExamen(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.zoneExamen = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ZoneExamen();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.zoneExamen = entity;
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
