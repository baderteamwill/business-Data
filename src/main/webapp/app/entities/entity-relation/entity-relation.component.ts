import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEntityRelation } from 'app/shared/model/entity-relation.model';
import { EntityRelationService } from './entity-relation.service';
import { EntityRelationDeleteDialogComponent } from './entity-relation-delete-dialog.component';

@Component({
  selector: 'jhi-entity-relation',
  templateUrl: './entity-relation.component.html'
})
export class EntityRelationComponent implements OnInit, OnDestroy {
  entityRelations?: IEntityRelation[];
  eventSubscriber?: Subscription;

  constructor(
    protected entityRelationService: EntityRelationService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.entityRelationService.query().subscribe((res: HttpResponse<IEntityRelation[]>) => {
      this.entityRelations = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEntityRelations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEntityRelation): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEntityRelations(): void {
    this.eventSubscriber = this.eventManager.subscribe('entityRelationListModification', () => this.loadAll());
  }

  delete(entityRelation: IEntityRelation): void {
    const modalRef = this.modalService.open(EntityRelationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.entityRelation = entityRelation;
  }
}
