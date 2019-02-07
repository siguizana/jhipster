/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { TypeEtablissementDetailComponent } from 'app/entities/type-etablissement/type-etablissement-detail.component';
import { TypeEtablissement } from 'app/shared/model/type-etablissement.model';

describe('Component Tests', () => {
    describe('TypeEtablissement Management Detail Component', () => {
        let comp: TypeEtablissementDetailComponent;
        let fixture: ComponentFixture<TypeEtablissementDetailComponent>;
        const route = ({ data: of({ typeEtablissement: new TypeEtablissement(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [TypeEtablissementDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TypeEtablissementDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeEtablissementDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.typeEtablissement).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
