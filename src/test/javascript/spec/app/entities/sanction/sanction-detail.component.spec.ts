/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { SanctionDetailComponent } from 'app/entities/sanction/sanction-detail.component';
import { Sanction } from 'app/shared/model/sanction.model';

describe('Component Tests', () => {
    describe('Sanction Management Detail Component', () => {
        let comp: SanctionDetailComponent;
        let fixture: ComponentFixture<SanctionDetailComponent>;
        const route = ({ data: of({ sanction: new Sanction(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [SanctionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SanctionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SanctionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.sanction).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
