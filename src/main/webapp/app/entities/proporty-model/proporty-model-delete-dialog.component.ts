import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProportyModel } from 'app/shared/model/proporty-model.model';
import { ProportyModelService } from './proporty-model.service';

@Component({
  templateUrl: './proporty-model-delete-dialog.component.html'
})
export class ProportyModelDeleteDialogComponent {
  proportyModel?: IProportyModel;

  constructor(
    protected proportyModelService: ProportyModelService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.proportyModelService.delete(id).subscribe(() => {
      this.eventManager.broadcast('proportyModelListModification');
      this.activeModal.close();
    });
  }
}
