import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BusinessDataTestModule } from '../../../test.module';
import { EntityRelationComponent } from 'app/entities/entity-relation/entity-relation.component';
import { EntityRelationService } from 'app/entities/entity-relation/entity-relation.service';
import { EntityRelation } from 'app/shared/model/entity-relation.model';

describe('Component Tests', () => {
  describe('EntityRelation Management Component', () => {
    let comp: EntityRelationComponent;
    let fixture: ComponentFixture<EntityRelationComponent>;
    let service: EntityRelationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BusinessDataTestModule],
        declarations: [EntityRelationComponent],
        providers: []
      })
        .overrideTemplate(EntityRelationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EntityRelationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EntityRelationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EntityRelation('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.entityRelations && comp.entityRelations[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
