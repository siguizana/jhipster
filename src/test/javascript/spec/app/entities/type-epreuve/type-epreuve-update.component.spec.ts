/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { TypeEpreuveUpdateComponent } from 'app/entities/type-epreuve/type-epreuve-update.component';
import { TypeEpreuveService } from 'app/entities/type-epreuve/type-epreuve.service';
import { TypeEpreuve } from 'app/shared/model/type-epreuve.model';

describe('Component Tests', () => {
    describe('TypeEpreuve Management Update Component', () => {
        let comp: TypeEpreuveUpdateComponent;
        let fixture: ComponentFixture<TypeEpreuveUpdateComponent>;
        let service: TypeEpreuveService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [TypeEpreuveUpdateComponent]
            })
                .overrideTemplate(TypeEpreuveUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TypeEpreuveUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeEpreuveService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new TypeEpreuve(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.typeEpreuve = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new TypeEpreuve();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.typeEpreuve = entity;
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
