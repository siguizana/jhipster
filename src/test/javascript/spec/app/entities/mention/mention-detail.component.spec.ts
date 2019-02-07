/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { MentionDetailComponent } from 'app/entities/mention/mention-detail.component';
import { Mention } from 'app/shared/model/mention.model';

describe('Component Tests', () => {
    describe('Mention Management Detail Component', () => {
        let comp: MentionDetailComponent;
        let fixture: ComponentFixture<MentionDetailComponent>;
        const route = ({ data: of({ mention: new Mention(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [MentionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MentionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MentionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.mention).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
