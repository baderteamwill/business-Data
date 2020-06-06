import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IEntityRelation, EntityRelation } from 'app/shared/model/entity-relation.model';
import { EntityRelationService } from './entity-relation.service';
import { IEntityModel } from 'app/shared/model/entity-model.model';
import { EntityModelService } from 'app/entities/entity-model/entity-model.service';

@Component({
  selector: 'jhi-entity-relation-update',
  templateUrl: './entity-relation-update.component.html'
})
export class EntityRelationUpdateComponent implements OnInit {
  isSaving = false;

  entitymodels: IEntityModel[] = [];

  entitymodel2s: IEntityModel[] = [];

  editForm = this.fb.group({
    id: [],
    relation: [],
    entityModel: [],
    entityModel2: []
  });

  constructor(
    protected entityRelationService: EntityRelationService,
    protected entityModelService: EntityModelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ entityRelation }) => {
      this.updateForm(entityRelation);

      this.entityModelService
        .query({ filter: 'entityrelation-is-null' })
        .pipe(
          map((res: HttpResponse<IEntityModel[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IEntityModel[]) => {
          if (!entityRelation.entityModel || !entityRelation.entityModel.id) {
            this.entitymodels = resBody;
          } else {
            this.entityModelService
              .find(entityRelation.entityModel.id)
              .pipe(
                map((subRes: HttpResponse<IEntityModel>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IEntityModel[]) => {
                this.entitymodels = concatRes;
              });
          }
        });

      this.entityModelService
        .query({ filter: 'entityrelation2-is-null' })
        .pipe(
          map((res: HttpResponse<IEntityModel[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IEntityModel[]) => {
          if (!entityRelation.entityModel2 || !entityRelation.entityModel2.id) {
            this.entitymodel2s = resBody;
          } else {
            this.entityModelService
              .find(entityRelation.entityModel2.id)
              .pipe(
                map((subRes: HttpResponse<IEntityModel>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IEntityModel[]) => {
                this.entitymodel2s = concatRes;
              });
          }
        });
    });
  }

  updateForm(entityRelation: IEntityRelation): void {
    this.editForm.patchValue({
      id: entityRelation.id,
      relation: entityRelation.relation,
      entityModel: entityRelation.entityModel,
      entityModel2: entityRelation.entityModel2
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const entityRelation = this.createFromForm();
    if (entityRelation.id !== undefined) {
      this.subscribeToSaveResponse(this.entityRelationService.update(entityRelation));
    } else {
      this.subscribeToSaveResponse(this.entityRelationService.create(entityRelation));
    }
  }

  private createFromForm(): IEntityRelation {
    return {
      ...new EntityRelation(),
      id: this.editForm.get(['id'])!.value,
      relation: this.editForm.get(['relation'])!.value,
      entityModel: this.editForm.get(['entityModel'])!.value,
      entityModel2: this.editForm.get(['entityModel2'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEntityRelation>>): void {
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
