import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInstanceRelation } from 'app/shared/model/instance-relation.model';

@Component({
  selector: 'jhi-instance-relation-detail',
  templateUrl: './instance-relation-detail.component.html'
})
export class InstanceRelationDetailComponent implements OnInit {
  instanceRelation: IInstanceRelation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ instanceRelation }) => {
      this.instanceRelation = instanceRelation;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
