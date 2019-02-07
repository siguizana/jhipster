/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { ChoixEtablissementDetailComponent } from 'app/entities/choix-etablissement/choix-etablissement-detail.component';
import { ChoixEtablissement } from 'app/shared/model/choix-etablissement.model';

describe('Component Tests', () => {
    describe('ChoixEtablissement Management Detail Component', () => {
        let comp: ChoixEtablissementDetailComponent;
        let fixture: ComponentFixture<ChoixEtablissementDetailComponent>;
        const route = ({ data: of({ choixEtablissement: new ChoixEtablissement(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [ChoixEtablissementDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ChoixEtablissementDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ChoixEtablissementDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.choixEtablissement).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
