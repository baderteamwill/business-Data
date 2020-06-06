import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IEntityinstance, Entityinstance } from 'app/shared/model/entityinstance.model';
import { EntityinstanceService } from './entityinstance.service';
import { IEntityModel } from 'app/shared/model/entity-model.model';
import { EntityModelService } from 'app/entities/entity-model/entity-model.service';

@Component({
  selector: 'jhi-entityinstance-update',
  templateUrl: './entityinstance-update.component.html'
})
export class EntityinstanceUpdateComponent implements OnInit {
  isSaving = false;

  entitymodels: IEntityModel[] = [];

  editForm = this.fb.group({
    id: [],
    instanceName: [],
    entityModel: []
  });

  constructor(
    protected entityinstanceService: EntityinstanceService,
    protected entityModelService: EntityModelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ entityinstance }) => {
      this.updateForm(entityinstance);

      this.entityModelService
        .query()
        .pipe(
          map((res: HttpResponse<IEntityModel[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IEntityModel[]) => (this.entitymodels = resBody));
    });
  }

  updateForm(entityinstance: IEntityinstance): void {
    this.editForm.patchValue({
      id: entityinstance.id,
      instanceName: entityinstance.instanceName,
      entityModel: entityinstance.entityModel
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const entityinstance = this.createFromForm();
    if (entityinstance.id !== undefined) {
      this.subscribeToSaveResponse(this.entityinstanceService.update(entityinstance));
    } else {
      this.subscribeToSaveResponse(this.entityinstanceService.create(entityinstance));
    }
  }

  private createFromForm(): IEntityinstance {
    return {
      ...new Entityinstance(),
      id: this.editForm.get(['id'])!.value,
      instanceName: this.editForm.get(['instanceName'])!.value,
      entityModel: this.editForm.get(['entityModel'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEntityinstance>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IEntityModel): any {
    return item.id;
  }
}
