import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEntityInstance } from 'app/shared/model/entity-instance.model';

@Component({
  selector: 'jhi-entity-instance-detail',
  templateUrl: './entity-instance-detail.component.html'
})
export class EntityInstanceDetailComponent implements OnInit {
  entityInstance: IEntityInstance | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ entityInstance }) => {
      this.entityInstance = entityInstance;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
