/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { GroupeEpreuveDetailComponent } from 'app/entities/groupe-epreuve/groupe-epreuve-detail.component';
import { GroupeEpreuve } from 'app/shared/model/groupe-epreuve.model';

describe('Component Tests', () => {
    describe('GroupeEpreuve Management Detail Component', () => {
        let comp: GroupeEpreuveDetailComponent;
        let fixture: ComponentFixture<GroupeEpreuveDetailComponent>;
        const route = ({ data: of({ groupeEpreuve: new GroupeEpreuve(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [GroupeEpreuveDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GroupeEpreuveDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GroupeEpreuveDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.groupeEpreuve).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
