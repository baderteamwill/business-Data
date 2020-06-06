import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEntityInstance } from 'app/shared/model/entity-instance.model';
import { EntityInstanceService } from './entity-instance.service';

@Component({
  templateUrl: './entity-instance-delete-dialog.component.html'
})
export class EntityInstanceDeleteDialogComponent {
  entityInstance?: IEntityInstance;

  constructor(
    protected entityInstanceService: EntityInstanceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.entityInstanceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('entityInstanceListModification');
      this.activeModal.close();
    });
  }
}
