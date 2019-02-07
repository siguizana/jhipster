/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { EnseigneUpdateComponent } from 'app/entities/enseigne/enseigne-update.component';
import { EnseigneService } from 'app/entities/enseigne/enseigne.service';
import { Enseigne } from 'app/shared/model/enseigne.model';

describe('Component Tests', () => {
    describe('Enseigne Management Update Component', () => {
        let comp: EnseigneUpdateComponent;
        let fixture: ComponentFixture<EnseigneUpdateComponent>;
        let service: EnseigneService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [EnseigneUpdateComponent]
            })
                .overrideTemplate(EnseigneUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EnseigneUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EnseigneService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Enseigne(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.enseigne = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Enseigne();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.enseigne = entity;
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
