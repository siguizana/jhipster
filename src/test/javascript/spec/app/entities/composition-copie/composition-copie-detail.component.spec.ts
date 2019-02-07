/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { CompositionCopieDetailComponent } from 'app/entities/composition-copie/composition-copie-detail.component';
import { CompositionCopie } from 'app/shared/model/composition-copie.model';

describe('Component Tests', () => {
    describe('CompositionCopie Management Detail Component', () => {
        let comp: CompositionCopieDetailComponent;
        let fixture: ComponentFixture<CompositionCopieDetailComponent>;
        const route = ({ data: of({ compositionCopie: new CompositionCopie(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [CompositionCopieDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CompositionCopieDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CompositionCopieDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.compositionCopie).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
