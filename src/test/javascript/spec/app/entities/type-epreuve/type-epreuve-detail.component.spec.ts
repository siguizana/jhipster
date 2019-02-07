/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { TypeEpreuveDetailComponent } from 'app/entities/type-epreuve/type-epreuve-detail.component';
import { TypeEpreuve } from 'app/shared/model/type-epreuve.model';

describe('Component Tests', () => {
    describe('TypeEpreuve Management Detail Component', () => {
        let comp: TypeEpreuveDetailComponent;
        let fixture: ComponentFixture<TypeEpreuveDetailComponent>;
        const route = ({ data: of({ typeEpreuve: new TypeEpreuve(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [TypeEpreuveDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TypeEpreuveDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeEpreuveDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.typeEpreuve).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
