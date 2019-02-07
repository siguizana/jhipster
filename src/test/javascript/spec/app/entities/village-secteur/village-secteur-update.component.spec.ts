/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { VillageSecteurUpdateComponent } from 'app/entities/village-secteur/village-secteur-update.component';
import { VillageSecteurService } from 'app/entities/village-secteur/village-secteur.service';
import { VillageSecteur } from 'app/shared/model/village-secteur.model';

describe('Component Tests', () => {
    describe('VillageSecteur Management Update Component', () => {
        let comp: VillageSecteurUpdateComponent;
        let fixture: ComponentFixture<VillageSecteurUpdateComponent>;
        let service: VillageSecteurService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [VillageSecteurUpdateComponent]
            })
                .overrideTemplate(VillageSecteurUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VillageSecteurUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VillageSecteurService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new VillageSecteur(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.villageSecteur = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new VillageSecteur();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.villageSecteur = entity;
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
