import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IProportyData, ProportyData } from 'app/shared/model/proporty-data.model';
import { ProportyDataService } from './proporty-data.service';
import { IEntityInstance } from 'app/shared/model/entity-instance.model';
import { EntityInstanceService } from 'app/entities/entity-instance/entity-instance.service';
import { IProportyModel } from 'app/shared/model/proporty-model.model';
import { ProportyModelService } from 'app/entities/proporty-model/proporty-model.service';

type SelectableEntity = IEntityInstance | IProportyModel;

@Component({
  selector: 'jhi-proporty-data-update',
  templateUrl: './proporty-data-update.component.html'
})
export class ProportyDataUpdateComponent implements OnInit {
  isSaving = false;

  entityinstances: IEntityInstance[] = [];

  proportymodels: IProportyModel[] = [];

  editForm = this.fb.group({
    id: [],
    proportyValue: [],
    entityInstance: [],
    proportyModel: []
  });

  constructor(
    protected proportyDataService: ProportyDataService,
    protected entityInstanceService: EntityInstanceService,
    protected proportyModelService: ProportyModelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ proportyData }) => {
      this.updateForm(proportyData);

      this.entityInstanceService
        .query()
        .pipe(
          map((res: HttpResponse<IEntityInstance[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IEntityInstance[]) => (this.entityinstances = resBody));

      this.proportyModelService
        .query({ filter: 'proportydata-is-null' })
        .pipe(
          map((res: HttpResponse<IProportyModel[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IProportyModel[]) => {
          if (!proportyData.proportyModel || !proportyData.proportyModel.id) {
            this.proportymodels = resBody;
          } else {
            this.proportyModelService
              .find(proportyData.proportyModel.id)
              .pipe(
                map((subRes: HttpResponse<IProportyModel>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IProportyModel[]) => {
                this.proportymodels = concatRes;
              });
          }
        });
    });
  }

  updateForm(proportyData: IProportyData): void {
    this.editForm.patchValue({
      id: proportyData.id,
      proportyValue: proportyData.proportyValue,
      entityInstance: proportyData.entityInstance,
      proportyModel: proportyData.proportyModel
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const proportyData = this.createFromForm();
    if (proportyData.id !== undefined) {
      this.subscribeToSaveResponse(this.proportyDataService.update(proportyData));
    } else {
      this.subscribeToSaveResponse(this.proportyDataService.create(proportyData));
    }
  }

  private createFromForm(): IProportyData {
    return {
      ...new ProportyData(),
      id: this.editForm.get(['id'])!.value,
      proportyValue: this.editForm.get(['proportyValue'])!.value,
      entityInstance: this.editForm.get(['entityInstance'])!.value,
      proportyModel: this.editForm.get(['proportyModel'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProportyData>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
