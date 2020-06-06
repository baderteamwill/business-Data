import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BusinessDataTestModule } from '../../../test.module';
import { ProportyModelDetailComponent } from 'app/entities/proporty-model/proporty-model-detail.component';
import { ProportyModel } from 'app/shared/model/proporty-model.model';

describe('Component Tests', () => {
  describe('ProportyModel Management Detail Component', () => {
    let comp: ProportyModelDetailComponent;
    let fixture: ComponentFixture<ProportyModelDetailComponent>;
    const route = ({ data: of({ proportyModel: new ProportyModel('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BusinessDataTestModule],
        declarations: [ProportyModelDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProportyModelDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProportyModelDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load proportyModel on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.proportyModel).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
