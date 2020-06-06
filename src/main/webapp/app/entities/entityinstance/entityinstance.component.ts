import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEntityinstance } from 'app/shared/model/entityinstance.model';
import { EntityinstanceService } from './entityinstance.service';
import { EntityinstanceDeleteDialogComponent } from './entityinstance-delete-dialog.component';

@Component({
  selector: 'jhi-entityinstance',
  templateUrl: './entityinstance.component.html'
})
export class EntityinstanceComponent implements OnInit, OnDestroy {
  entityinstances?: IEntityinstance[];
  eventSubscriber?: Subscription;

  constructor(
    protected entityinstanceService: EntityinstanceService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.entityinstanceService.query().subscribe((res: HttpResponse<IEntityinstance[]>) => {
      this.entityinstances = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEntityinstances();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEntityinstance): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEntityinstances(): void {
    this.eventSubscriber = this.eventManager.subscribe('entityinstanceListModification', () => this.loadAll());
  }

  delete(entityinstance: IEntityinstance): void {
    const modalRef = this.modalService.open(EntityinstanceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.entityinstance = entityinstance;
  }
}
