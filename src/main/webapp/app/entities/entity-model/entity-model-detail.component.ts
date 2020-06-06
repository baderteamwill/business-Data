import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEntityModel } from 'app/shared/model/entity-model.model';

@Component({
  selector: 'jhi-entity-model-detail',
  templateUrl: './entity-model-detail.component.html'
})
export class EntityModelDetailComponent implements OnInit {
  entityModel: IEntityModel | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ entityModel }) => {
      this.entityModel = entityModel;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
