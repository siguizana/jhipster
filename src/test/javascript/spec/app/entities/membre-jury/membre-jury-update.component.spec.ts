/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { MembreJuryUpdateComponent } from 'app/entities/membre-jury/membre-jury-update.component';
import { MembreJuryService } from 'app/entities/membre-jury/membre-jury.service';
import { MembreJury } from 'app/shared/model/membre-jury.model';

describe('Component Tests', () => {
    describe('MembreJury Management Update Component', () => {
        let comp: MembreJuryUpdateComponent;
        let fixture: ComponentFixture<MembreJuryUpdateComponent>;
        let service: MembreJuryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [MembreJuryUpdateComponent]
            })
                .overrideTemplate(MembreJuryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MembreJuryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MembreJuryService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new MembreJury(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.membreJury = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new MembreJury();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.membreJury = entity;
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
