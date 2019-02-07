/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { OptionConcoursRattacheUpdateComponent } from 'app/entities/option-concours-rattache/option-concours-rattache-update.component';
import { OptionConcoursRattacheService } from 'app/entities/option-concours-rattache/option-concours-rattache.service';
import { OptionConcoursRattache } from 'app/shared/model/option-concours-rattache.model';

describe('Component Tests', () => {
    describe('OptionConcoursRattache Management Update Component', () => {
        let comp: OptionConcoursRattacheUpdateComponent;
        let fixture: ComponentFixture<OptionConcoursRattacheUpdateComponent>;
        let service: OptionConcoursRattacheService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [OptionConcoursRattacheUpdateComponent]
            })
                .overrideTemplate(OptionConcoursRattacheUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OptionConcoursRattacheUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OptionConcoursRattacheService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new OptionConcoursRattache(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.optionConcoursRattache = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new OptionConcoursRattache();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.optionConcoursRattache = entity;
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
