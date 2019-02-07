/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { TypeDecisionDetailComponent } from 'app/entities/type-decision/type-decision-detail.component';
import { TypeDecision } from 'app/shared/model/type-decision.model';

describe('Component Tests', () => {
    describe('TypeDecision Management Detail Component', () => {
        let comp: TypeDecisionDetailComponent;
        let fixture: ComponentFixture<TypeDecisionDetailComponent>;
        const route = ({ data: of({ typeDecision: new TypeDecision(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [TypeDecisionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TypeDecisionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeDecisionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.typeDecision).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
