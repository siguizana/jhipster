/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { RetraitDiplomeUpdateComponent } from 'app/entities/retrait-diplome/retrait-diplome-update.component';
import { RetraitDiplomeService } from 'app/entities/retrait-diplome/retrait-diplome.service';
import { RetraitDiplome } from 'app/shared/model/retrait-diplome.model';

describe('Component Tests', () => {
    describe('RetraitDiplome Management Update Component', () => {
        let comp: RetraitDiplomeUpdateComponent;
        let fixture: ComponentFixture<RetraitDiplomeUpdateComponent>;
        let service: RetraitDiplomeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [RetraitDiplomeUpdateComponent]
            })
                .overrideTemplate(RetraitDiplomeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RetraitDiplomeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RetraitDiplomeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new RetraitDiplome(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.retraitDiplome = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new RetraitDiplome();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.retraitDiplome = entity;
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
