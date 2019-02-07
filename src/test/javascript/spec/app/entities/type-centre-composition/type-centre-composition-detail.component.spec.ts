/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { TypeCentreCompositionDetailComponent } from 'app/entities/type-centre-composition/type-centre-composition-detail.component';
import { TypeCentreComposition } from 'app/shared/model/type-centre-composition.model';

describe('Component Tests', () => {
    describe('TypeCentreComposition Management Detail Component', () => {
        let comp: TypeCentreCompositionDetailComponent;
        let fixture: ComponentFixture<TypeCentreCompositionDetailComponent>;
        const route = ({ data: of({ typeCentreComposition: new TypeCentreComposition(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [TypeCentreCompositionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TypeCentreCompositionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeCentreCompositionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.typeCentreComposition).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
