/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { CebDetailComponent } from 'app/entities/ceb/ceb-detail.component';
import { Ceb } from 'app/shared/model/ceb.model';

describe('Component Tests', () => {
    describe('Ceb Management Detail Component', () => {
        let comp: CebDetailComponent;
        let fixture: ComponentFixture<CebDetailComponent>;
        const route = ({ data: of({ ceb: new Ceb(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [CebDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CebDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CebDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.ceb).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
