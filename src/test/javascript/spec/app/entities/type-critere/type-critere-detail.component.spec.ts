/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { TypeCritereDetailComponent } from 'app/entities/type-critere/type-critere-detail.component';
import { TypeCritere } from 'app/shared/model/type-critere.model';

describe('Component Tests', () => {
    describe('TypeCritere Management Detail Component', () => {
        let comp: TypeCritereDetailComponent;
        let fixture: ComponentFixture<TypeCritereDetailComponent>;
        const route = ({ data: of({ typeCritere: new TypeCritere(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [TypeCritereDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TypeCritereDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeCritereDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.typeCritere).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
