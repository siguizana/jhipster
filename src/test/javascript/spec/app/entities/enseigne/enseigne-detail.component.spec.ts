/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { EnseigneDetailComponent } from 'app/entities/enseigne/enseigne-detail.component';
import { Enseigne } from 'app/shared/model/enseigne.model';

describe('Component Tests', () => {
    describe('Enseigne Management Detail Component', () => {
        let comp: EnseigneDetailComponent;
        let fixture: ComponentFixture<EnseigneDetailComponent>;
        const route = ({ data: of({ enseigne: new Enseigne(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [EnseigneDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EnseigneDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EnseigneDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.enseigne).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
