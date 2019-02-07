/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { TypeExamenDetailComponent } from 'app/entities/type-examen/type-examen-detail.component';
import { TypeExamen } from 'app/shared/model/type-examen.model';

describe('Component Tests', () => {
    describe('TypeExamen Management Detail Component', () => {
        let comp: TypeExamenDetailComponent;
        let fixture: ComponentFixture<TypeExamenDetailComponent>;
        const route = ({ data: of({ typeExamen: new TypeExamen(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [TypeExamenDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TypeExamenDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeExamenDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.typeExamen).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
