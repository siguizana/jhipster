/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { CritereExamenDetailComponent } from 'app/entities/critere-examen/critere-examen-detail.component';
import { CritereExamen } from 'app/shared/model/critere-examen.model';

describe('Component Tests', () => {
    describe('CritereExamen Management Detail Component', () => {
        let comp: CritereExamenDetailComponent;
        let fixture: ComponentFixture<CritereExamenDetailComponent>;
        const route = ({ data: of({ critereExamen: new CritereExamen(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [CritereExamenDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CritereExamenDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CritereExamenDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.critereExamen).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
