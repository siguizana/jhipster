/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { DispenseUpdateComponent } from 'app/entities/dispense/dispense-update.component';
import { DispenseService } from 'app/entities/dispense/dispense.service';
import { Dispense } from 'app/shared/model/dispense.model';

describe('Component Tests', () => {
    describe('Dispense Management Update Component', () => {
        let comp: DispenseUpdateComponent;
        let fixture: ComponentFixture<DispenseUpdateComponent>;
        let service: DispenseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [DispenseUpdateComponent]
            })
                .overrideTemplate(DispenseUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DispenseUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DispenseService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Dispense(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.dispense = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Dispense();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.dispense = entity;
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
