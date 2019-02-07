/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { ConcoursRattacheDetailComponent } from 'app/entities/concours-rattache/concours-rattache-detail.component';
import { ConcoursRattache } from 'app/shared/model/concours-rattache.model';

describe('Component Tests', () => {
    describe('ConcoursRattache Management Detail Component', () => {
        let comp: ConcoursRattacheDetailComponent;
        let fixture: ComponentFixture<ConcoursRattacheDetailComponent>;
        const route = ({ data: of({ concoursRattache: new ConcoursRattache(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [ConcoursRattacheDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ConcoursRattacheDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ConcoursRattacheDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.concoursRattache).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
