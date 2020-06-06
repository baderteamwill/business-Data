import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDatabaseModel } from 'app/shared/model/database-model.model';
import { DatabaseModelService } from './database-model.service';
import { DatabaseModelDeleteDialogComponent } from './database-model-delete-dialog.component';

@Component({
  selector: 'jhi-database-model',
  templateUrl: './database-model.component.html'
})
export class DatabaseModelComponent implements OnInit, OnDestroy {
  databaseModels?: IDatabaseModel[];
  eventSubscriber?: Subscription;

  constructor(
    protected databaseModelService: DatabaseModelService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.databaseModelService.query().subscribe((res: HttpResponse<IDatabaseModel[]>) => {
      this.databaseModels = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDatabaseModels();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDatabaseModel): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDatabaseModels(): void {
    this.eventSubscriber = this.eventManager.subscribe('databaseModelListModification', () => this.loadAll());
  }

  delete(databaseModel: IDatabaseModel): void {
    const modalRef = this.modalService.open(DatabaseModelDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.databaseModel = databaseModel;
  }
}
