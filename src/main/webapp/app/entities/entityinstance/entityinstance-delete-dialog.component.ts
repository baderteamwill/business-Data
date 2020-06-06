import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEntityinstance } from 'app/shared/model/entityinstance.model';
import { EntityinstanceService } from './entityinstance.service';

@Component({
  templateUrl: './entityinstance-delete-dialog.component.html'
})
export class EntityinstanceDeleteDialogComponent {
  entityinstance?: IEntityinstance;

  constructor(
    protected entityinstanceService: EntityinstanceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.entityinstanceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('entityinstanceListModification');
      this.activeModal.close();
    });
  }
}
