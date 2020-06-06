import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProportyModel } from 'app/shared/model/proporty-model.model';
import { ProportyModelService } from './proporty-model.service';
import { ProportyModelDeleteDialogComponent } from './proporty-model-delete-dialog.component';

@Component({
  selector: 'jhi-proporty-model',
  templateUrl: './proporty-model.component.html'
})
export class ProportyModelComponent implements OnInit, OnDestroy {
  proportyModels?: IProportyModel[];
  eventSubscriber?: Subscription;

  constructor(
    protected proportyModelService: ProportyModelService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.proportyModelService.query().subscribe((res: HttpResponse<IProportyModel[]>) => {
      this.proportyModels = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProportyModels();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProportyModel): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProportyModels(): void {
    this.eventSubscriber = this.eventManager.subscribe('proportyModelListModification', () => this.loadAll());
  }

  delete(proportyModel: IProportyModel): void {
    const modalRef = this.modalService.open(ProportyModelDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.proportyModel = proportyModel;
  }
}
