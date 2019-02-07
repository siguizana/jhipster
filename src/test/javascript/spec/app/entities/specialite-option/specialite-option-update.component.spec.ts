/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { SpecialiteOptionUpdateComponent } from 'app/entities/specialite-option/specialite-option-update.component';
import { SpecialiteOptionService } from 'app/entities/specialite-option/specialite-option.service';
import { SpecialiteOption } from 'app/shared/model/specialite-option.model';

describe('Component Tests', () => {
    describe('SpecialiteOption Management Update Component', () => {
        let comp: SpecialiteOptionUpdateComponent;
        let fixture: ComponentFixture<SpecialiteOptionUpdateComponent>;
        let service: SpecialiteOptionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [SpecialiteOptionUpdateComponent]
            })
                .overrideTemplate(SpecialiteOptionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SpecialiteOptionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SpecialiteOptionService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new SpecialiteOption(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.specialiteOption = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new SpecialiteOption();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.specialiteOption = entity;
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
