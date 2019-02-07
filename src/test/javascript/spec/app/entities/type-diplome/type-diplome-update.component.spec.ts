/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { TypeDiplomeUpdateComponent } from 'app/entities/type-diplome/type-diplome-update.component';
import { TypeDiplomeService } from 'app/entities/type-diplome/type-diplome.service';
import { TypeDiplome } from 'app/shared/model/type-diplome.model';

describe('Component Tests', () => {
    describe('TypeDiplome Management Update Component', () => {
        let comp: TypeDiplomeUpdateComponent;
        let fixture: ComponentFixture<TypeDiplomeUpdateComponent>;
        let service: TypeDiplomeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [TypeDiplomeUpdateComponent]
            })
                .overrideTemplate(TypeDiplomeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TypeDiplomeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeDiplomeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new TypeDiplome(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.typeDiplome = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new TypeDiplome();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.typeDiplome = entity;
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
