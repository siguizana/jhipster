/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { EpreuveSpecialiteOptionDetailComponent } from 'app/entities/epreuve-specialite-option/epreuve-specialite-option-detail.component';
import { EpreuveSpecialiteOption } from 'app/shared/model/epreuve-specialite-option.model';

describe('Component Tests', () => {
    describe('EpreuveSpecialiteOption Management Detail Component', () => {
        let comp: EpreuveSpecialiteOptionDetailComponent;
        let fixture: ComponentFixture<EpreuveSpecialiteOptionDetailComponent>;
        const route = ({ data: of({ epreuveSpecialiteOption: new EpreuveSpecialiteOption(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [EpreuveSpecialiteOptionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EpreuveSpecialiteOptionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EpreuveSpecialiteOptionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.epreuveSpecialiteOption).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
