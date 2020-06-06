import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEntityRelation } from 'app/shared/model/entity-relation.model';

@Component({
  selector: 'jhi-entity-relation-detail',
  templateUrl: './entity-relation-detail.component.html'
})
export class EntityRelationDetailComponent implements OnInit {
  entityRelation: IEntityRelation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ entityRelation }) => {
      this.entityRelation = entityRelation;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
