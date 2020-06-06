import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BusinessDataTestModule } from '../../../test.module';
import { InstanceRelationComponent } from 'app/entities/instance-relation/instance-relation.component';
import { InstanceRelationService } from 'app/entities/instance-relation/instance-relation.service';
import { InstanceRelation } from 'app/shared/model/instance-relation.model';

describe('Component Tests', () => {
  describe('InstanceRelation Management Component', () => {
    let comp: InstanceRelationComponent;
    let fixture: ComponentFixture<InstanceRelationComponent>;
    let service: InstanceRelationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BusinessDataTestModule],
        declarations: [InstanceRelationComponent],
        providers: []
      })
        .overrideTemplate(InstanceRelationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InstanceRelationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InstanceRelationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new InstanceRelation('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.instanceRelations && comp.instanceRelations[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
