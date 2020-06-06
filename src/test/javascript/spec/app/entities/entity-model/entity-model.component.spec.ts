import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BusinessDataTestModule } from '../../../test.module';
import { EntityModelComponent } from 'app/entities/entity-model/entity-model.component';
import { EntityModelService } from 'app/entities/entity-model/entity-model.service';
import { EntityModel } from 'app/shared/model/entity-model.model';

describe('Component Tests', () => {
  describe('EntityModel Management Component', () => {
    let comp: EntityModelComponent;
    let fixture: ComponentFixture<EntityModelComponent>;
    let service: EntityModelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BusinessDataTestModule],
        declarations: [EntityModelComponent],
        providers: []
      })
        .overrideTemplate(EntityModelComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EntityModelComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EntityModelService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EntityModel('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.entityModels && comp.entityModels[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
