/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { TypeCentreCompositionUpdateComponent } from 'app/entities/type-centre-composition/type-centre-composition-update.component';
import { TypeCentreCompositionService } from 'app/entities/type-centre-composition/type-centre-composition.service';
import { TypeCentreComposition } from 'app/shared/model/type-centre-composition.model';

describe('Component Tests', () => {
    describe('TypeCentreComposition Management Update Component', () => {
        let comp: TypeCentreCompositionUpdateComponent;
        let fixture: ComponentFixture<TypeCentreCompositionUpdateComponent>;
        let service: TypeCentreCompositionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [TypeCentreCompositionUpdateComponent]
            })
                .overrideTemplate(TypeCentreCompositionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TypeCentreCompositionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeCentreCompositionService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new TypeCentreComposition(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.typeCentreComposition = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new TypeCentreComposition();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.typeCentreComposition = entity;
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
