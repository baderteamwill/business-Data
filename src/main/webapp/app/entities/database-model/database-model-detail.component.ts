import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDatabaseModel } from 'app/shared/model/database-model.model';

@Component({
  selector: 'jhi-database-model-detail',
  templateUrl: './database-model-detail.component.html'
})
export class DatabaseModelDetailComponent implements OnInit {
  databaseModel: IDatabaseModel | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ databaseModel }) => {
      this.databaseModel = databaseModel;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
