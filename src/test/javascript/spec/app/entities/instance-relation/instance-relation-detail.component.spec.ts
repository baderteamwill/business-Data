import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BusinessDataTestModule } from '../../../test.module';
import { InstanceRelationDetailComponent } from 'app/entities/instance-relation/instance-relation-detail.component';
import { InstanceRelation } from 'app/shared/model/instance-relation.model';

describe('Component Tests', () => {
  describe('InstanceRelation Management Detail Component', () => {
    let comp: InstanceRelationDetailComponent;
    let fixture: ComponentFixture<InstanceRelationDetailComponent>;
    const route = ({ data: of({ instanceRelation: new InstanceRelation('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BusinessDataTestModule],
        declarations: [InstanceRelationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InstanceRelationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InstanceRelationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load instanceRelation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.instanceRelation).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
