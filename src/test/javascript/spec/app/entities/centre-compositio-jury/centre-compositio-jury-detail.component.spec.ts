/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { CentreCompositioJuryDetailComponent } from 'app/entities/centre-compositio-jury/centre-compositio-jury-detail.component';
import { CentreCompositioJury } from 'app/shared/model/centre-compositio-jury.model';

describe('Component Tests', () => {
    describe('CentreCompositioJury Management Detail Component', () => {
        let comp: CentreCompositioJuryDetailComponent;
        let fixture: ComponentFixture<CentreCompositioJuryDetailComponent>;
        const route = ({ data: of({ centreCompositioJury: new CentreCompositioJury(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [CentreCompositioJuryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CentreCompositioJuryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CentreCompositioJuryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.centreCompositioJury).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
