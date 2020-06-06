import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IInstanceRelation, InstanceRelation } from 'app/shared/model/instance-relation.model';
import { InstanceRelationService } from './instance-relation.service';
import { IEntityInstance } from 'app/shared/model/entity-instance.model';
import { EntityInstanceService } from 'app/entities/entity-instance/entity-instance.service';
import { IEntityRelation } from 'app/shared/model/entity-relation.model';
import { EntityRelationService } from 'app/entities/entity-relation/entity-relation.service';

type SelectableEntity = IEntityInstance | IEntityRelation;

@Component({
  selector: 'jhi-instance-relation-update',
  templateUrl: './instance-relation-update.component.html'
})
export class InstanceRelationUpdateComponent implements OnInit {
  isSaving = false;

  entityinstances: IEntityInstance[] = [];

  entityinstance2s: IEntityInstance[] = [];

  entityrelations: IEntityRelation[] = [];

  editForm = this.fb.group({
    id: [],
    entityInstance: [],
    entityInstance2: [],
    entityRelation: []
  });

  constructor(
    protected instanceRelationService: InstanceRelationService,
    protected entityInstanceService: EntityInstanceService,
    protected entityRelationService: EntityRelationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ instanceRelation }) => {
      this.updateForm(instanceRelation);

      this.entityInstanceService
        .query({ filter: 'instancerelation-is-null' })
        .pipe(
          map((res: HttpResponse<IEntityInstance[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IEntityInstance[]) => {
          if (!instanceRelation.entityInstance || !instanceRelation.entityInstance.id) {
            this.entityinstances = resBody;
          } else {
            this.entityInstanceService
              .find(instanceRelation.entityInstance.id)
              .pipe(
                map((subRes: HttpResponse<IEntityInstance>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IEntityInstance[]) => {
                this.entityinstances = concatRes;
              });
          }
        });

      this.entityInstanceService
        .query({ filter: 'instancerelation2-is-null' })
        .pipe(
          map((res: HttpResponse<IEntityInstance[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IEntityInstance[]) => {
          if (!instanceRelation.entityInstance2 || !instanceRelation.entityInstance2.id) {
            this.entityinstance2s = resBody;
          } else {
            this.entityInstanceService
              .find(instanceRelation.entityInstance2.id)
              .pipe(
                map((subRes: HttpResponse<IEntityInstance>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IEntityInstance[]) => {
                this.entityinstance2s = concatRes;
              });
          }
        });

      this.entityRelationService
        .query()
        .pipe(
          map((res: HttpResponse<IEntityRelation[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IEntityRelation[]) => (this.entityrelations = resBody));
    });
  }

  updateForm(instanceRelation: IInstanceRelation): void {
    this.editForm.patchValue({
      id: instanceRelation.id,
      entityInstance: instanceRelation.entityInstance,
      entityInstance2: instanceRelation.entityInstance2,
      entityRelation: instanceRelation.entityRelation
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const instanceRelation = this.createFromForm();
    if (instanceRelation.id !== undefined) {
      this.subscribeToSaveResponse(this.instanceRelationService.update(instanceRelation));
    } else {
      this.subscribeToSaveResponse(this.instanceRelationService.create(instanceRelation));
    }
  }

  private createFromForm(): IInstanceRelation {
    return {
      ...new InstanceRelation(),
      id: this.editForm.get(['id'])!.value,
      entityInstance: this.editForm.get(['entityInstance'])!.value,
      entityInstance2: this.editForm.get(['entityInstance2'])!.value,
      entityRelation: this.editForm.get(['entityRelation'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInstanceRelation>>): void {
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
