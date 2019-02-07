/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { ChoixEtablissementUpdateComponent } from 'app/entities/choix-etablissement/choix-etablissement-update.component';
import { ChoixEtablissementService } from 'app/entities/choix-etablissement/choix-etablissement.service';
import { ChoixEtablissement } from 'app/shared/model/choix-etablissement.model';

describe('Component Tests', () => {
    describe('ChoixEtablissement Management Update Component', () => {
        let comp: ChoixEtablissementUpdateComponent;
        let fixture: ComponentFixture<ChoixEtablissementUpdateComponent>;
        let service: ChoixEtablissementService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [ChoixEtablissementUpdateComponent]
            })
                .overrideTemplate(ChoixEtablissementUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ChoixEtablissementUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChoixEtablissementService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ChoixEtablissement(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.choixEtablissement = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ChoixEtablissement();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.choixEtablissement = entity;
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
