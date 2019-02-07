/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { EpreuveUpdateComponent } from 'app/entities/epreuve/epreuve-update.component';
import { EpreuveService } from 'app/entities/epreuve/epreuve.service';
import { Epreuve } from 'app/shared/model/epreuve.model';

describe('Component Tests', () => {
    describe('Epreuve Management Update Component', () => {
        let comp: EpreuveUpdateComponent;
        let fixture: ComponentFixture<EpreuveUpdateComponent>;
        let service: EpreuveService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [EpreuveUpdateComponent]
            })
                .overrideTemplate(EpreuveUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EpreuveUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EpreuveService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Epreuve(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.epreuve = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Epreuve();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.epreuve = entity;
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
