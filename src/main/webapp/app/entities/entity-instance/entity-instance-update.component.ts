import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IEntityInstance, EntityInstance } from 'app/shared/model/entity-instance.model';
import { EntityInstanceService } from './entity-instance.service';
import { IEntityModel } from 'app/shared/model/entity-model.model';
import { EntityModelService } from 'app/entities/entity-model/entity-model.service';

@Component({
  selector: 'jhi-entity-instance-update',
  templateUrl: './entity-instance-update.component.html'
})
export class EntityInstanceUpdateComponent implements OnInit {
  isSaving = false;

  entitymodels: IEntityModel[] = [];

  editForm = this.fb.group({
    id: [],
    instanceName: [],
    entityModel: []
  });

  constructor(
    protected entityInstanceService: EntityInstanceService,
    protected entityModelService: EntityModelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ entityInstance }) => {
      this.updateForm(entityInstance);

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

  updateForm(entityInstance: IEntityInstance): void {
    this.editForm.patchValue({
      id: entityInstance.id,
      instanceName: entityInstance.instanceName,
      entityModel: entityInstance.entityModel
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const entityInstance = this.createFromForm();
    if (entityInstance.id !== undefined) {
      this.subscribeToSaveResponse(this.entityInstanceService.update(entityInstance));
    } else {
      this.subscribeToSaveResponse(this.entityInstanceService.create(entityInstance));
    }
  }

  private createFromForm(): IEntityInstance {
    return {
      ...new EntityInstance(),
      id: this.editForm.get(['id'])!.value,
      instanceName: this.editForm.get(['instanceName'])!.value,
      entityModel: this.editForm.get(['entityModel'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEntityInstance>>): void {
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
