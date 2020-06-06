import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IEntityModel, EntityModel } from 'app/shared/model/entity-model.model';
import { EntityModelService } from './entity-model.service';
import { IDatabaseModel } from 'app/shared/model/database-model.model';
import { DatabaseModelService } from 'app/entities/database-model/database-model.service';

@Component({
  selector: 'jhi-entity-model-update',
  templateUrl: './entity-model-update.component.html'
})
export class EntityModelUpdateComponent implements OnInit {
  isSaving = false;

  databasemodels: IDatabaseModel[] = [];

  editForm = this.fb.group({
    id: [],
    entityName: [],
    entityDescription: [],
    databaseModel: []
  });

  constructor(
    protected entityModelService: EntityModelService,
    protected databaseModelService: DatabaseModelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ entityModel }) => {
      this.updateForm(entityModel);

      this.databaseModelService
        .query()
        .pipe(
          map((res: HttpResponse<IDatabaseModel[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IDatabaseModel[]) => (this.databasemodels = resBody));
    });
  }

  updateForm(entityModel: IEntityModel): void {
    this.editForm.patchValue({
      id: entityModel.id,
      entityName: entityModel.entityName,
      entityDescription: entityModel.entityDescription,
      databaseModel: entityModel.databaseModel
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const entityModel = this.createFromForm();
    if (entityModel.id !== undefined) {
      this.subscribeToSaveResponse(this.entityModelService.update(entityModel));
    } else {
      this.subscribeToSaveResponse(this.entityModelService.create(entityModel));
    }
  }

  private createFromForm(): IEntityModel {
    return {
      ...new EntityModel(),
      id: this.editForm.get(['id'])!.value,
      entityName: this.editForm.get(['entityName'])!.value,
      entityDescription: this.editForm.get(['entityDescription'])!.value,
      databaseModel: this.editForm.get(['databaseModel'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEntityModel>>): void {
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

  trackById(index: number, item: IDatabaseModel): any {
    return item.id;
  }
}
