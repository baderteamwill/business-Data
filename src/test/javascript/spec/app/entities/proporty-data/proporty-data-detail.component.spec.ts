import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BusinessDataTestModule } from '../../../test.module';
import { ProportyDataDetailComponent } from 'app/entities/proporty-data/proporty-data-detail.component';
import { ProportyData } from 'app/shared/model/proporty-data.model';

describe('Component Tests', () => {
  describe('ProportyData Management Detail Component', () => {
    let comp: ProportyDataDetailComponent;
    let fixture: ComponentFixture<ProportyDataDetailComponent>;
    const route = ({ data: of({ proportyData: new ProportyData('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BusinessDataTestModule],
        declarations: [ProportyDataDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProportyDataDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProportyDataDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load proportyData on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.proportyData).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
