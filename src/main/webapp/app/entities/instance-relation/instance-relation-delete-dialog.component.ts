import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInstanceRelation } from 'app/shared/model/instance-relation.model';
import { InstanceRelationService } from './instance-relation.service';

@Component({
  templateUrl: './instance-relation-delete-dialog.component.html'
})
export class InstanceRelationDeleteDialogComponent {
  instanceRelation?: IInstanceRelation;

  constructor(
    protected instanceRelationService: InstanceRelationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.instanceRelationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('instanceRelationListModification');
      this.activeModal.close();
    });
  }
}
