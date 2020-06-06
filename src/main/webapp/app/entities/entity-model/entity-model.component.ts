import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEntityModel } from 'app/shared/model/entity-model.model';
import { EntityModelService } from './entity-model.service';
import { EntityModelDeleteDialogComponent } from './entity-model-delete-dialog.component';

@Component({
  selector: 'jhi-entity-model',
  templateUrl: './entity-model.component.html'
})
export class EntityModelComponent implements OnInit, OnDestroy {
  entityModels?: IEntityModel[];
  eventSubscriber?: Subscription;

  constructor(
    protected entityModelService: EntityModelService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.entityModelService.query().subscribe((res: HttpResponse<IEntityModel[]>) => {
      this.entityModels = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEntityModels();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEntityModel): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEntityModels(): void {
    this.eventSubscriber = this.eventManager.subscribe('entityModelListModification', () => this.loadAll());
  }

  delete(entityModel: IEntityModel): void {
    const modalRef = this.modalService.open(EntityModelDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.entityModel = entityModel;
  }
}
