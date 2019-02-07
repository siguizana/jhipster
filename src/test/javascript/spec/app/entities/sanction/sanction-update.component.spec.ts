/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { SanctionUpdateComponent } from 'app/entities/sanction/sanction-update.component';
import { SanctionService } from 'app/entities/sanction/sanction.service';
import { Sanction } from 'app/shared/model/sanction.model';

describe('Component Tests', () => {
    describe('Sanction Management Update Component', () => {
        let comp: SanctionUpdateComponent;
        let fixture: ComponentFixture<SanctionUpdateComponent>;
        let service: SanctionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [SanctionUpdateComponent]
            })
                .overrideTemplate(SanctionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SanctionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SanctionService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Sanction(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.sanction = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Sanction();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.sanction = entity;
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
