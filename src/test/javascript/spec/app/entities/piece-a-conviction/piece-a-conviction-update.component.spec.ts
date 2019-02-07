/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { PieceAConvictionUpdateComponent } from 'app/entities/piece-a-conviction/piece-a-conviction-update.component';
import { PieceAConvictionService } from 'app/entities/piece-a-conviction/piece-a-conviction.service';
import { PieceAConviction } from 'app/shared/model/piece-a-conviction.model';

describe('Component Tests', () => {
    describe('PieceAConviction Management Update Component', () => {
        let comp: PieceAConvictionUpdateComponent;
        let fixture: ComponentFixture<PieceAConvictionUpdateComponent>;
        let service: PieceAConvictionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [PieceAConvictionUpdateComponent]
            })
                .overrideTemplate(PieceAConvictionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PieceAConvictionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PieceAConvictionService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new PieceAConviction(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.pieceAConviction = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new PieceAConviction();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.pieceAConviction = entity;
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
