/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { OptionConcoursRattacheDetailComponent } from 'app/entities/option-concours-rattache/option-concours-rattache-detail.component';
import { OptionConcoursRattache } from 'app/shared/model/option-concours-rattache.model';

describe('Component Tests', () => {
    describe('OptionConcoursRattache Management Detail Component', () => {
        let comp: OptionConcoursRattacheDetailComponent;
        let fixture: ComponentFixture<OptionConcoursRattacheDetailComponent>;
        const route = ({ data: of({ optionConcoursRattache: new OptionConcoursRattache(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [OptionConcoursRattacheDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(OptionConcoursRattacheDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OptionConcoursRattacheDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.optionConcoursRattache).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
