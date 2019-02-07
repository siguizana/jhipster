/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { FraudeUpdateComponent } from 'app/entities/fraude/fraude-update.component';
import { FraudeService } from 'app/entities/fraude/fraude.service';
import { Fraude } from 'app/shared/model/fraude.model';

describe('Component Tests', () => {
    describe('Fraude Management Update Component', () => {
        let comp: FraudeUpdateComponent;
        let fixture: ComponentFixture<FraudeUpdateComponent>;
        let service: FraudeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [FraudeUpdateComponent]
            })
                .overrideTemplate(FraudeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FraudeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FraudeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Fraude(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.fraude = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Fraude();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.fraude = entity;
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
