import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProportyData } from 'app/shared/model/proporty-data.model';
import { ProportyDataService } from './proporty-data.service';

@Component({
  templateUrl: './proporty-data-delete-dialog.component.html'
})
export class ProportyDataDeleteDialogComponent {
  proportyData?: IProportyData;

  constructor(
    protected proportyDataService: ProportyDataService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.proportyDataService.delete(id).subscribe(() => {
      this.eventManager.broadcast('proportyDataListModification');
      this.activeModal.close();
    });
  }
}
