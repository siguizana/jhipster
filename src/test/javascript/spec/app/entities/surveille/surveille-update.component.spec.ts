/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { SurveilleUpdateComponent } from 'app/entities/surveille/surveille-update.component';
import { SurveilleService } from 'app/entities/surveille/surveille.service';
import { Surveille } from 'app/shared/model/surveille.model';

describe('Component Tests', () => {
    describe('Surveille Management Update Component', () => {
        let comp: SurveilleUpdateComponent;
        let fixture: ComponentFixture<SurveilleUpdateComponent>;
        let service: SurveilleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [SurveilleUpdateComponent]
            })
                .overrideTemplate(SurveilleUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SurveilleUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SurveilleService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Surveille(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.surveille = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Surveille();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.surveille = entity;
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
