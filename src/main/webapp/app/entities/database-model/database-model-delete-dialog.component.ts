import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDatabaseModel } from 'app/shared/model/database-model.model';
import { DatabaseModelService } from './database-model.service';

@Component({
  templateUrl: './database-model-delete-dialog.component.html'
})
export class DatabaseModelDeleteDialogComponent {
  databaseModel?: IDatabaseModel;

  constructor(
    protected databaseModelService: DatabaseModelService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.databaseModelService.delete(id).subscribe(() => {
      this.eventManager.broadcast('databaseModelListModification');
      this.activeModal.close();
    });
  }
}
