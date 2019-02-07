/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { TypeFraudeDetailComponent } from 'app/entities/type-fraude/type-fraude-detail.component';
import { TypeFraude } from 'app/shared/model/type-fraude.model';

describe('Component Tests', () => {
    describe('TypeFraude Management Detail Component', () => {
        let comp: TypeFraudeDetailComponent;
        let fixture: ComponentFixture<TypeFraudeDetailComponent>;
        const route = ({ data: of({ typeFraude: new TypeFraude(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [TypeFraudeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TypeFraudeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeFraudeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.typeFraude).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
