/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { FraudeDetailComponent } from 'app/entities/fraude/fraude-detail.component';
import { Fraude } from 'app/shared/model/fraude.model';

describe('Component Tests', () => {
    describe('Fraude Management Detail Component', () => {
        let comp: FraudeDetailComponent;
        let fixture: ComponentFixture<FraudeDetailComponent>;
        const route = ({ data: of({ fraude: new Fraude(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [FraudeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FraudeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FraudeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.fraude).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
