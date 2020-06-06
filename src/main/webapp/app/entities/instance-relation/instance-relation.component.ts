import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInstanceRelation } from 'app/shared/model/instance-relation.model';
import { InstanceRelationService } from './instance-relation.service';
import { InstanceRelationDeleteDialogComponent } from './instance-relation-delete-dialog.component';

@Component({
  selector: 'jhi-instance-relation',
  templateUrl: './instance-relation.component.html'
})
export class InstanceRelationComponent implements OnInit, OnDestroy {
  instanceRelations?: IInstanceRelation[];
  eventSubscriber?: Subscription;

  constructor(
    protected instanceRelationService: InstanceRelationService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.instanceRelationService.query().subscribe((res: HttpResponse<IInstanceRelation[]>) => {
      this.instanceRelations = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInInstanceRelations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IInstanceRelation): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInInstanceRelations(): void {
    this.eventSubscriber = this.eventManager.subscribe('instanceRelationListModification', () => this.loadAll());
  }

  delete(instanceRelation: IInstanceRelation): void {
    const modalRef = this.modalService.open(InstanceRelationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.instanceRelation = instanceRelation;
  }
}
