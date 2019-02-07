/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { CompositionCopieUpdateComponent } from 'app/entities/composition-copie/composition-copie-update.component';
import { CompositionCopieService } from 'app/entities/composition-copie/composition-copie.service';
import { CompositionCopie } from 'app/shared/model/composition-copie.model';

describe('Component Tests', () => {
    describe('CompositionCopie Management Update Component', () => {
        let comp: CompositionCopieUpdateComponent;
        let fixture: ComponentFixture<CompositionCopieUpdateComponent>;
        let service: CompositionCopieService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [CompositionCopieUpdateComponent]
            })
                .overrideTemplate(CompositionCopieUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CompositionCopieUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompositionCopieService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CompositionCopie(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.compositionCopie = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CompositionCopie();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.compositionCopie = entity;
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
