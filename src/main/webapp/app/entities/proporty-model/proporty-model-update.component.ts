import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IProportyModel, ProportyModel } from 'app/shared/model/proporty-model.model';
import { ProportyModelService } from './proporty-model.service';
import { IEntityModel } from 'app/shared/model/entity-model.model';
import { EntityModelService } from 'app/entities/entity-model/entity-model.service';

@Component({
  selector: 'jhi-proporty-model-update',
  templateUrl: './proporty-model-update.component.html'
})
export class ProportyModelUpdateComponent implements OnInit {
  isSaving = false;

  entitymodels: IEntityModel[] = [];

  editForm = this.fb.group({
    id: [],
    proportyName: [],
    proportyType: [],
    entityModel: []
  });

  constructor(
    protected proportyModelService: ProportyModelService,
    protected entityModelService: EntityModelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ proportyModel }) => {
      this.updateForm(proportyModel);

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

  updateForm(proportyModel: IProportyModel): void {
    this.editForm.patchValue({
      id: proportyModel.id,
      proportyName: proportyModel.proportyName,
      proportyType: proportyModel.proportyType,
      entityModel: proportyModel.entityModel
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const proportyModel = this.createFromForm();
    if (proportyModel.id !== undefined) {
      this.subscribeToSaveResponse(this.proportyModelService.update(proportyModel));
    } else {
      this.subscribeToSaveResponse(this.proportyModelService.create(proportyModel));
    }
  }

  private createFromForm(): IProportyModel {
    return {
      ...new ProportyModel(),
      id: this.editForm.get(['id'])!.value,
      proportyName: this.editForm.get(['proportyName'])!.value,
      proportyType: this.editForm.get(['proportyType'])!.value,
      entityModel: this.editForm.get(['entityModel'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProportyModel>>): void {
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
