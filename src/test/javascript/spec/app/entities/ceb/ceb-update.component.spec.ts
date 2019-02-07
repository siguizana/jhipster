/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { CebUpdateComponent } from 'app/entities/ceb/ceb-update.component';
import { CebService } from 'app/entities/ceb/ceb.service';
import { Ceb } from 'app/shared/model/ceb.model';

describe('Component Tests', () => {
    describe('Ceb Management Update Component', () => {
        let comp: CebUpdateComponent;
        let fixture: ComponentFixture<CebUpdateComponent>;
        let service: CebService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [CebUpdateComponent]
            })
                .overrideTemplate(CebUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CebUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CebService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Ceb(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.ceb = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Ceb();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.ceb = entity;
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
