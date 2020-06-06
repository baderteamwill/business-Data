import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BusinessDataTestModule } from '../../../test.module';
import { DatabaseModelDetailComponent } from 'app/entities/database-model/database-model-detail.component';
import { DatabaseModel } from 'app/shared/model/database-model.model';

describe('Component Tests', () => {
  describe('DatabaseModel Management Detail Component', () => {
    let comp: DatabaseModelDetailComponent;
    let fixture: ComponentFixture<DatabaseModelDetailComponent>;
    const route = ({ data: of({ databaseModel: new DatabaseModel('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BusinessDataTestModule],
        declarations: [DatabaseModelDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DatabaseModelDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DatabaseModelDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load databaseModel on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.databaseModel).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
