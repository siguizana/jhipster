/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { TypeExamenUpdateComponent } from 'app/entities/type-examen/type-examen-update.component';
import { TypeExamenService } from 'app/entities/type-examen/type-examen.service';
import { TypeExamen } from 'app/shared/model/type-examen.model';

describe('Component Tests', () => {
    describe('TypeExamen Management Update Component', () => {
        let comp: TypeExamenUpdateComponent;
        let fixture: ComponentFixture<TypeExamenUpdateComponent>;
        let service: TypeExamenService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [TypeExamenUpdateComponent]
            })
                .overrideTemplate(TypeExamenUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TypeExamenUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeExamenService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new TypeExamen(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.typeExamen = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new TypeExamen();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.typeExamen = entity;
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
