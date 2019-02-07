/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { VillageSecteurDetailComponent } from 'app/entities/village-secteur/village-secteur-detail.component';
import { VillageSecteur } from 'app/shared/model/village-secteur.model';

describe('Component Tests', () => {
    describe('VillageSecteur Management Detail Component', () => {
        let comp: VillageSecteurDetailComponent;
        let fixture: ComponentFixture<VillageSecteurDetailComponent>;
        const route = ({ data: of({ villageSecteur: new VillageSecteur(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [VillageSecteurDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(VillageSecteurDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VillageSecteurDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.villageSecteur).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
