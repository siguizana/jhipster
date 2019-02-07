/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { CentreExamenDetailComponent } from 'app/entities/centre-examen/centre-examen-detail.component';
import { CentreExamen } from 'app/shared/model/centre-examen.model';

describe('Component Tests', () => {
    describe('CentreExamen Management Detail Component', () => {
        let comp: CentreExamenDetailComponent;
        let fixture: ComponentFixture<CentreExamenDetailComponent>;
        const route = ({ data: of({ centreExamen: new CentreExamen(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [CentreExamenDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CentreExamenDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CentreExamenDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.centreExamen).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
