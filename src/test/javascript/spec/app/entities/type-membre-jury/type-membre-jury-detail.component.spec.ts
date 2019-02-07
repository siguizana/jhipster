/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { TypeMembreJuryDetailComponent } from 'app/entities/type-membre-jury/type-membre-jury-detail.component';
import { TypeMembreJury } from 'app/shared/model/type-membre-jury.model';

describe('Component Tests', () => {
    describe('TypeMembreJury Management Detail Component', () => {
        let comp: TypeMembreJuryDetailComponent;
        let fixture: ComponentFixture<TypeMembreJuryDetailComponent>;
        const route = ({ data: of({ typeMembreJury: new TypeMembreJury(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [TypeMembreJuryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TypeMembreJuryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeMembreJuryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.typeMembreJury).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
