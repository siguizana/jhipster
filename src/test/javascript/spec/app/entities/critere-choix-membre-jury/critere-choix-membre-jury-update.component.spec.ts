/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { CritereChoixMembreJuryUpdateComponent } from 'app/entities/critere-choix-membre-jury/critere-choix-membre-jury-update.component';
import { CritereChoixMembreJuryService } from 'app/entities/critere-choix-membre-jury/critere-choix-membre-jury.service';
import { CritereChoixMembreJury } from 'app/shared/model/critere-choix-membre-jury.model';

describe('Component Tests', () => {
    describe('CritereChoixMembreJury Management Update Component', () => {
        let comp: CritereChoixMembreJuryUpdateComponent;
        let fixture: ComponentFixture<CritereChoixMembreJuryUpdateComponent>;
        let service: CritereChoixMembreJuryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [CritereChoixMembreJuryUpdateComponent]
            })
                .overrideTemplate(CritereChoixMembreJuryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CritereChoixMembreJuryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CritereChoixMembreJuryService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CritereChoixMembreJury(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.critereChoixMembreJury = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CritereChoixMembreJury();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.critereChoixMembreJury = entity;
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
