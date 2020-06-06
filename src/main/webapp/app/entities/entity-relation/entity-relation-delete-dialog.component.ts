import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEntityRelation } from 'app/shared/model/entity-relation.model';
import { EntityRelationService } from './entity-relation.service';

@Component({
  templateUrl: './entity-relation-delete-dialog.component.html'
})
export class EntityRelationDeleteDialogComponent {
  entityRelation?: IEntityRelation;

  constructor(
    protected entityRelationService: EntityRelationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.entityRelationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('entityRelationListModification');
      this.activeModal.close();
    });
  }
}
