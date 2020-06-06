import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEntityModel } from 'app/shared/model/entity-model.model';
import { EntityModelService } from './entity-model.service';

@Component({
  templateUrl: './entity-model-delete-dialog.component.html'
})
export class EntityModelDeleteDialogComponent {
  entityModel?: IEntityModel;

  constructor(
    protected entityModelService: EntityModelService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.entityModelService.delete(id).subscribe(() => {
      this.eventManager.broadcast('entityModelListModification');
      this.activeModal.close();
    });
  }
}
