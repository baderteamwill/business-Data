import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BusinessDataTestModule } from '../../../test.module';
import { EntityInstanceComponent } from 'app/entities/entity-instance/entity-instance.component';
import { EntityInstanceService } from 'app/entities/entity-instance/entity-instance.service';
import { EntityInstance } from 'app/shared/model/entity-instance.model';

describe('Component Tests', () => {
  describe('EntityInstance Management Component', () => {
    let comp: EntityInstanceComponent;
    let fixture: ComponentFixture<EntityInstanceComponent>;
    let service: EntityInstanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BusinessDataTestModule],
        declarations: [EntityInstanceComponent],
        providers: []
      })
        .overrideTemplate(EntityInstanceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EntityInstanceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EntityInstanceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EntityInstance('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.entityInstances && comp.entityInstances[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
