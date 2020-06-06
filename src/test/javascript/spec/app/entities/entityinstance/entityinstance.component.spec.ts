import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BusinessDataTestModule } from '../../../test.module';
import { EntityinstanceComponent } from 'app/entities/entityinstance/entityinstance.component';
import { EntityinstanceService } from 'app/entities/entityinstance/entityinstance.service';
import { Entityinstance } from 'app/shared/model/entityinstance.model';

describe('Component Tests', () => {
  describe('Entityinstance Management Component', () => {
    let comp: EntityinstanceComponent;
    let fixture: ComponentFixture<EntityinstanceComponent>;
    let service: EntityinstanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BusinessDataTestModule],
        declarations: [EntityinstanceComponent],
        providers: []
      })
        .overrideTemplate(EntityinstanceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EntityinstanceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EntityinstanceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Entityinstance('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.entityinstances && comp.entityinstances[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
