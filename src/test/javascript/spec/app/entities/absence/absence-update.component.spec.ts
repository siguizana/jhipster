/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { AbsenceUpdateComponent } from 'app/entities/absence/absence-update.component';
import { AbsenceService } from 'app/entities/absence/absence.service';
import { Absence } from 'app/shared/model/absence.model';

describe('Component Tests', () => {
    describe('Absence Management Update Component', () => {
        let comp: AbsenceUpdateComponent;
        let fixture: ComponentFixture<AbsenceUpdateComponent>;
        let service: AbsenceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [AbsenceUpdateComponent]
            })
                .overrideTemplate(AbsenceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AbsenceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AbsenceService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Absence(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.absence = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Absence();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.absence = entity;
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
