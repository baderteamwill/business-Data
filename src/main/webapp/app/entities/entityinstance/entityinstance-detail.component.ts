import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEntityinstance } from 'app/shared/model/entityinstance.model';

@Component({
  selector: 'jhi-entityinstance-detail',
  templateUrl: './entityinstance-detail.component.html'
})
export class EntityinstanceDetailComponent implements OnInit {
  entityinstance: IEntityinstance | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ entityinstance }) => {
      this.entityinstance = entityinstance;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
