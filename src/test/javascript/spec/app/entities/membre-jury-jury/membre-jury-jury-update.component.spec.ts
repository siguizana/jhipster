/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { MembreJuryJuryUpdateComponent } from 'app/entities/membre-jury-jury/membre-jury-jury-update.component';
import { MembreJuryJuryService } from 'app/entities/membre-jury-jury/membre-jury-jury.service';
import { MembreJuryJury } from 'app/shared/model/membre-jury-jury.model';

describe('Component Tests', () => {
    describe('MembreJuryJury Management Update Component', () => {
        let comp: MembreJuryJuryUpdateComponent;
        let fixture: ComponentFixture<MembreJuryJuryUpdateComponent>;
        let service: MembreJuryJuryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [MembreJuryJuryUpdateComponent]
            })
                .overrideTemplate(MembreJuryJuryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MembreJuryJuryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MembreJuryJuryService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new MembreJuryJury(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.membreJuryJury = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new MembreJuryJury();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.membreJuryJury = entity;
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
