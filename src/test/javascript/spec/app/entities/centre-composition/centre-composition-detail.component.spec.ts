/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { CentreCompositionDetailComponent } from 'app/entities/centre-composition/centre-composition-detail.component';
import { CentreComposition } from 'app/shared/model/centre-composition.model';

describe('Component Tests', () => {
    describe('CentreComposition Management Detail Component', () => {
        let comp: CentreCompositionDetailComponent;
        let fixture: ComponentFixture<CentreCompositionDetailComponent>;
        const route = ({ data: of({ centreComposition: new CentreComposition(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [CentreCompositionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CentreCompositionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CentreCompositionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.centreComposition).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
