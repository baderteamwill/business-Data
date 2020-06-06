import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProportyData } from 'app/shared/model/proporty-data.model';

@Component({
  selector: 'jhi-proporty-data-detail',
  templateUrl: './proporty-data-detail.component.html'
})
export class ProportyDataDetailComponent implements OnInit {
  proportyData: IProportyData | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ proportyData }) => {
      this.proportyData = proportyData;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
