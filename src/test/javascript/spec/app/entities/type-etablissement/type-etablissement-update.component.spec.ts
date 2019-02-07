/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { TypeEtablissementUpdateComponent } from 'app/entities/type-etablissement/type-etablissement-update.component';
import { TypeEtablissementService } from 'app/entities/type-etablissement/type-etablissement.service';
import { TypeEtablissement } from 'app/shared/model/type-etablissement.model';

describe('Component Tests', () => {
    describe('TypeEtablissement Management Update Component', () => {
        let comp: TypeEtablissementUpdateComponent;
        let fixture: ComponentFixture<TypeEtablissementUpdateComponent>;
        let service: TypeEtablissementService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [TypeEtablissementUpdateComponent]
            })
                .overrideTemplate(TypeEtablissementUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TypeEtablissementUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeEtablissementService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new TypeEtablissement(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.typeEtablissement = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new TypeEtablissement();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.typeEtablissement = entity;
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
