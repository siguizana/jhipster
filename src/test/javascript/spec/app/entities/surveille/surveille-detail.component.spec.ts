/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { SurveilleDetailComponent } from 'app/entities/surveille/surveille-detail.component';
import { Surveille } from 'app/shared/model/surveille.model';

describe('Component Tests', () => {
    describe('Surveille Management Detail Component', () => {
        let comp: SurveilleDetailComponent;
        let fixture: ComponentFixture<SurveilleDetailComponent>;
        const route = ({ data: of({ surveille: new Surveille(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [SurveilleDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SurveilleDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SurveilleDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.surveille).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
