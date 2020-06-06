import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProportyModel } from 'app/shared/model/proporty-model.model';

@Component({
  selector: 'jhi-proporty-model-detail',
  templateUrl: './proporty-model-detail.component.html'
})
export class ProportyModelDetailComponent implements OnInit {
  proportyModel: IProportyModel | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ proportyModel }) => {
      this.proportyModel = proportyModel;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
