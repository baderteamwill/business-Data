import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BusinessDataTestModule } from '../../../test.module';
import { DatabaseModelComponent } from 'app/entities/database-model/database-model.component';
import { DatabaseModelService } from 'app/entities/database-model/database-model.service';
import { DatabaseModel } from 'app/shared/model/database-model.model';

describe('Component Tests', () => {
  describe('DatabaseModel Management Component', () => {
    let comp: DatabaseModelComponent;
    let fixture: ComponentFixture<DatabaseModelComponent>;
    let service: DatabaseModelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BusinessDataTestModule],
        declarations: [DatabaseModelComponent],
        providers: []
      })
        .overrideTemplate(DatabaseModelComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DatabaseModelComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DatabaseModelService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DatabaseModel('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.databaseModels && comp.databaseModels[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
