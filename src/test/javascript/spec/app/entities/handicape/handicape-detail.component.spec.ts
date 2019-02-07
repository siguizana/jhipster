/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { HandicapeDetailComponent } from 'app/entities/handicape/handicape-detail.component';
import { Handicape } from 'app/shared/model/handicape.model';

describe('Component Tests', () => {
    describe('Handicape Management Detail Component', () => {
        let comp: HandicapeDetailComponent;
        let fixture: ComponentFixture<HandicapeDetailComponent>;
        const route = ({ data: of({ handicape: new Handicape(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [HandicapeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(HandicapeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HandicapeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.handicape).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
