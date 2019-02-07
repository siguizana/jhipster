/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { DeroulementScolariteUpdateComponent } from 'app/entities/deroulement-scolarite/deroulement-scolarite-update.component';
import { DeroulementScolariteService } from 'app/entities/deroulement-scolarite/deroulement-scolarite.service';
import { DeroulementScolarite } from 'app/shared/model/deroulement-scolarite.model';

describe('Component Tests', () => {
    describe('DeroulementScolarite Management Update Component', () => {
        let comp: DeroulementScolariteUpdateComponent;
        let fixture: ComponentFixture<DeroulementScolariteUpdateComponent>;
        let service: DeroulementScolariteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [DeroulementScolariteUpdateComponent]
            })
                .overrideTemplate(DeroulementScolariteUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DeroulementScolariteUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeroulementScolariteService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new DeroulementScolarite(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.deroulementScolarite = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new DeroulementScolarite();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.deroulementScolarite = entity;
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
