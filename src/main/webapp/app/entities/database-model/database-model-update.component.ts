import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDatabaseModel, DatabaseModel } from 'app/shared/model/database-model.model';
import { DatabaseModelService } from './database-model.service';

@Component({
  selector: 'jhi-database-model-update',
  templateUrl: './database-model-update.component.html'
})
export class DatabaseModelUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    databaseModel: [],
    databaseDescription: []
  });

  constructor(protected databaseModelService: DatabaseModelService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ databaseModel }) => {
      this.updateForm(databaseModel);
    });
  }

  updateForm(databaseModel: IDatabaseModel): void {
    this.editForm.patchValue({
      id: databaseModel.id,
      databaseModel: databaseModel.databaseModel,
      databaseDescription: databaseModel.databaseDescription
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const databaseModel = this.createFromForm();
    if (databaseModel.id !== undefined) {
      this.subscribeToSaveResponse(this.databaseModelService.update(databaseModel));
    } else {
      this.subscribeToSaveResponse(this.databaseModelService.create(databaseModel));
    }
  }

  private createFromForm(): IDatabaseModel {
    return {
      ...new DatabaseModel(),
      id: this.editForm.get(['id'])!.value,
      databaseModel: this.editForm.get(['databaseModel'])!.value,
      databaseDescription: this.editForm.get(['databaseDescription'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDatabaseModel>>): void {
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
}
