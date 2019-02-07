/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { TypeDiplomeDetailComponent } from 'app/entities/type-diplome/type-diplome-detail.component';
import { TypeDiplome } from 'app/shared/model/type-diplome.model';

describe('Component Tests', () => {
    describe('TypeDiplome Management Detail Component', () => {
        let comp: TypeDiplomeDetailComponent;
        let fixture: ComponentFixture<TypeDiplomeDetailComponent>;
        const route = ({ data: of({ typeDiplome: new TypeDiplome(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [TypeDiplomeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TypeDiplomeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeDiplomeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.typeDiplome).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
