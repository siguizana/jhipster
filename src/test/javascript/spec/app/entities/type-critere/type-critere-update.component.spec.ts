/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { TypeCritereUpdateComponent } from 'app/entities/type-critere/type-critere-update.component';
import { TypeCritereService } from 'app/entities/type-critere/type-critere.service';
import { TypeCritere } from 'app/shared/model/type-critere.model';

describe('Component Tests', () => {
    describe('TypeCritere Management Update Component', () => {
        let comp: TypeCritereUpdateComponent;
        let fixture: ComponentFixture<TypeCritereUpdateComponent>;
        let service: TypeCritereService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [TypeCritereUpdateComponent]
            })
                .overrideTemplate(TypeCritereUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TypeCritereUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeCritereService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new TypeCritere(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.typeCritere = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new TypeCritere();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.typeCritere = entity;
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
