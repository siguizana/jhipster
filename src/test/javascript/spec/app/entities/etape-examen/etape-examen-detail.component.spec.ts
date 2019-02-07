/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { EtapeExamenDetailComponent } from 'app/entities/etape-examen/etape-examen-detail.component';
import { EtapeExamen } from 'app/shared/model/etape-examen.model';

describe('Component Tests', () => {
    describe('EtapeExamen Management Detail Component', () => {
        let comp: EtapeExamenDetailComponent;
        let fixture: ComponentFixture<EtapeExamenDetailComponent>;
        const route = ({ data: of({ etapeExamen: new EtapeExamen(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [EtapeExamenDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EtapeExamenDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EtapeExamenDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.etapeExamen).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
