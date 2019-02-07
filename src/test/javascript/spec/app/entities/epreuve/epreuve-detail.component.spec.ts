/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { EpreuveDetailComponent } from 'app/entities/epreuve/epreuve-detail.component';
import { Epreuve } from 'app/shared/model/epreuve.model';

describe('Component Tests', () => {
    describe('Epreuve Management Detail Component', () => {
        let comp: EpreuveDetailComponent;
        let fixture: ComponentFixture<EpreuveDetailComponent>;
        const route = ({ data: of({ epreuve: new Epreuve(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [EpreuveDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EpreuveDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EpreuveDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.epreuve).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
