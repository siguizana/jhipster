/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { CritereChoixMembreJuryDetailComponent } from 'app/entities/critere-choix-membre-jury/critere-choix-membre-jury-detail.component';
import { CritereChoixMembreJury } from 'app/shared/model/critere-choix-membre-jury.model';

describe('Component Tests', () => {
    describe('CritereChoixMembreJury Management Detail Component', () => {
        let comp: CritereChoixMembreJuryDetailComponent;
        let fixture: ComponentFixture<CritereChoixMembreJuryDetailComponent>;
        const route = ({ data: of({ critereChoixMembreJury: new CritereChoixMembreJury(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [CritereChoixMembreJuryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CritereChoixMembreJuryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CritereChoixMembreJuryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.critereChoixMembreJury).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
