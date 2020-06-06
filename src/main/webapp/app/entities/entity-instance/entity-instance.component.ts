import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEntityInstance } from 'app/shared/model/entity-instance.model';
import { EntityInstanceService } from './entity-instance.service';
import { EntityInstanceDeleteDialogComponent } from './entity-instance-delete-dialog.component';

@Component({
  selector: 'jhi-entity-instance',
  templateUrl: './entity-instance.component.html'
})
export class EntityInstanceComponent implements OnInit, OnDestroy {
  entityInstances?: IEntityInstance[];
  eventSubscriber?: Subscription;

  constructor(
    protected entityInstanceService: EntityInstanceService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.entityInstanceService.query().subscribe((res: HttpResponse<IEntityInstance[]>) => {
      this.entityInstances = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEntityInstances();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEntityInstance): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEntityInstances(): void {
    this.eventSubscriber = this.eventManager.subscribe('entityInstanceListModification', () => this.loadAll());
  }

  delete(entityInstance: IEntityInstance): void {
    const modalRef = this.modalService.open(EntityInstanceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.entityInstance = entityInstance;
  }
}
