/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { PieceAConvictionDetailComponent } from 'app/entities/piece-a-conviction/piece-a-conviction-detail.component';
import { PieceAConviction } from 'app/shared/model/piece-a-conviction.model';

describe('Component Tests', () => {
    describe('PieceAConviction Management Detail Component', () => {
        let comp: PieceAConvictionDetailComponent;
        let fixture: ComponentFixture<PieceAConvictionDetailComponent>;
        const route = ({ data: of({ pieceAConviction: new PieceAConviction(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [PieceAConvictionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PieceAConvictionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PieceAConvictionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.pieceAConviction).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
