/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { ZoneExamenDetailComponent } from 'app/entities/zone-examen/zone-examen-detail.component';
import { ZoneExamen } from 'app/shared/model/zone-examen.model';

describe('Component Tests', () => {
    describe('ZoneExamen Management Detail Component', () => {
        let comp: ZoneExamenDetailComponent;
        let fixture: ComponentFixture<ZoneExamenDetailComponent>;
        const route = ({ data: of({ zoneExamen: new ZoneExamen(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [ZoneExamenDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ZoneExamenDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ZoneExamenDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.zoneExamen).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
