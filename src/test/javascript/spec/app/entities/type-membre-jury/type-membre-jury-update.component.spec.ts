/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { TypeMembreJuryUpdateComponent } from 'app/entities/type-membre-jury/type-membre-jury-update.component';
import { TypeMembreJuryService } from 'app/entities/type-membre-jury/type-membre-jury.service';
import { TypeMembreJury } from 'app/shared/model/type-membre-jury.model';

describe('Component Tests', () => {
    describe('TypeMembreJury Management Update Component', () => {
        let comp: TypeMembreJuryUpdateComponent;
        let fixture: ComponentFixture<TypeMembreJuryUpdateComponent>;
        let service: TypeMembreJuryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [TypeMembreJuryUpdateComponent]
            })
                .overrideTemplate(TypeMembreJuryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TypeMembreJuryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeMembreJuryService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new TypeMembreJury(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.typeMembreJury = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new TypeMembreJury();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.typeMembreJury = entity;
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
