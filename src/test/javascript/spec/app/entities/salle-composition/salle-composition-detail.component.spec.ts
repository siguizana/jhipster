/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { SalleCompositionDetailComponent } from 'app/entities/salle-composition/salle-composition-detail.component';
import { SalleComposition } from 'app/shared/model/salle-composition.model';

describe('Component Tests', () => {
    describe('SalleComposition Management Detail Component', () => {
        let comp: SalleCompositionDetailComponent;
        let fixture: ComponentFixture<SalleCompositionDetailComponent>;
        const route = ({ data: of({ salleComposition: new SalleComposition(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [SalleCompositionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SalleCompositionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SalleCompositionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.salleComposition).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
