/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigecTestModule } from '../../../test.module';
import { NoteMembreCritereDetailComponent } from 'app/entities/note-membre-critere/note-membre-critere-detail.component';
import { NoteMembreCritere } from 'app/shared/model/note-membre-critere.model';

describe('Component Tests', () => {
    describe('NoteMembreCritere Management Detail Component', () => {
        let comp: NoteMembreCritereDetailComponent;
        let fixture: ComponentFixture<NoteMembreCritereDetailComponent>;
        const route = ({ data: of({ noteMembreCritere: new NoteMembreCritere(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SigecTestModule],
                declarations: [NoteMembreCritereDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(NoteMembreCritereDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(NoteMembreCritereDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.noteMembreCritere).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
