/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { CentreCompositioJuryUpdateComponent } from 'app/entities/centre-compositio-jury/centre-compositio-jury-update.component';
import { CentreCompositioJuryService } from 'app/entities/centre-compositio-jury/centre-compositio-jury.service';
import { CentreCompositioJury } from 'app/shared/model/centre-compositio-jury.model';

describe('Component Tests', () => {
    describe('CentreCompositioJury Management Update Component', () => {
        let comp: CentreCompositioJuryUpdateComponent;
        let fixture: ComponentFixture<CentreCompositioJuryUpdateComponent>;
        let service: CentreCompositioJuryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [CentreCompositioJuryUpdateComponent]
            })
                .overrideTemplate(CentreCompositioJuryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CentreCompositioJuryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CentreCompositioJuryService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CentreCompositioJury(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.centreCompositioJury = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CentreCompositioJury();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.centreCompositioJury = entity;
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
