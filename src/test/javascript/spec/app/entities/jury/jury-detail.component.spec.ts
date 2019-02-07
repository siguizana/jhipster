/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { JuryDetailComponent } from 'app/entities/jury/jury-detail.component';
import { Jury } from 'app/shared/model/jury.model';

describe('Component Tests', () => {
    describe('Jury Management Detail Component', () => {
        let comp: JuryDetailComponent;
        let fixture: ComponentFixture<JuryDetailComponent>;
        const route = ({ data: of({ jury: new Jury(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [JuryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(JuryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(JuryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.jury).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
