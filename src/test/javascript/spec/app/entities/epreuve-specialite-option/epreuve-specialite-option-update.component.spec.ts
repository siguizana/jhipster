/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { EpreuveSpecialiteOptionUpdateComponent } from 'app/entities/epreuve-specialite-option/epreuve-specialite-option-update.component';
import { EpreuveSpecialiteOptionService } from 'app/entities/epreuve-specialite-option/epreuve-specialite-option.service';
import { EpreuveSpecialiteOption } from 'app/shared/model/epreuve-specialite-option.model';

describe('Component Tests', () => {
    describe('EpreuveSpecialiteOption Management Update Component', () => {
        let comp: EpreuveSpecialiteOptionUpdateComponent;
        let fixture: ComponentFixture<EpreuveSpecialiteOptionUpdateComponent>;
        let service: EpreuveSpecialiteOptionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [EpreuveSpecialiteOptionUpdateComponent]
            })
                .overrideTemplate(EpreuveSpecialiteOptionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EpreuveSpecialiteOptionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EpreuveSpecialiteOptionService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new EpreuveSpecialiteOption(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.epreuveSpecialiteOption = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new EpreuveSpecialiteOption();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.epreuveSpecialiteOption = entity;
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
