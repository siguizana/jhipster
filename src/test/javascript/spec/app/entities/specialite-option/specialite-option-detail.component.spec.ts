/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { SpecialiteOptionDetailComponent } from 'app/entities/specialite-option/specialite-option-detail.component';
import { SpecialiteOption } from 'app/shared/model/specialite-option.model';

describe('Component Tests', () => {
    describe('SpecialiteOption Management Detail Component', () => {
        let comp: SpecialiteOptionDetailComponent;
        let fixture: ComponentFixture<SpecialiteOptionDetailComponent>;
        const route = ({ data: of({ specialiteOption: new SpecialiteOption(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [SpecialiteOptionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SpecialiteOptionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SpecialiteOptionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.specialiteOption).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
