import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BusinessDataTestModule } from '../../../test.module';
import { EntityModelDetailComponent } from 'app/entities/entity-model/entity-model-detail.component';
import { EntityModel } from 'app/shared/model/entity-model.model';

describe('Component Tests', () => {
  describe('EntityModel Management Detail Component', () => {
    let comp: EntityModelDetailComponent;
    let fixture: ComponentFixture<EntityModelDetailComponent>;
    const route = ({ data: of({ entityModel: new EntityModel('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BusinessDataTestModule],
        declarations: [EntityModelDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EntityModelDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EntityModelDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load entityModel on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.entityModel).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
