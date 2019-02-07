/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { ConcoursRattacheUpdateComponent } from 'app/entities/concours-rattache/concours-rattache-update.component';
import { ConcoursRattacheService } from 'app/entities/concours-rattache/concours-rattache.service';
import { ConcoursRattache } from 'app/shared/model/concours-rattache.model';

describe('Component Tests', () => {
    describe('ConcoursRattache Management Update Component', () => {
        let comp: ConcoursRattacheUpdateComponent;
        let fixture: ComponentFixture<ConcoursRattacheUpdateComponent>;
        let service: ConcoursRattacheService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [ConcoursRattacheUpdateComponent]
            })
                .overrideTemplate(ConcoursRattacheUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ConcoursRattacheUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConcoursRattacheService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ConcoursRattache(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.concoursRattache = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ConcoursRattache();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.concoursRattache = entity;
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
