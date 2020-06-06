import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BusinessDataTestModule } from '../../../test.module';
import { EntityRelationDetailComponent } from 'app/entities/entity-relation/entity-relation-detail.component';
import { EntityRelation } from 'app/shared/model/entity-relation.model';

describe('Component Tests', () => {
  describe('EntityRelation Management Detail Component', () => {
    let comp: EntityRelationDetailComponent;
    let fixture: ComponentFixture<EntityRelationDetailComponent>;
    const route = ({ data: of({ entityRelation: new EntityRelation('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BusinessDataTestModule],
        declarations: [EntityRelationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EntityRelationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EntityRelationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load entityRelation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.entityRelation).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
