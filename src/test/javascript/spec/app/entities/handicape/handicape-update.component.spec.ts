/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { HandicapeUpdateComponent } from 'app/entities/handicape/handicape-update.component';
import { HandicapeService } from 'app/entities/handicape/handicape.service';
import { Handicape } from 'app/shared/model/handicape.model';

describe('Component Tests', () => {
    describe('Handicape Management Update Component', () => {
        let comp: HandicapeUpdateComponent;
        let fixture: ComponentFixture<HandicapeUpdateComponent>;
        let service: HandicapeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [HandicapeUpdateComponent]
            })
                .overrideTemplate(HandicapeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HandicapeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HandicapeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Handicape(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.handicape = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Handicape();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.handicape = entity;
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
