import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProportyData } from 'app/shared/model/proporty-data.model';
import { ProportyDataService } from './proporty-data.service';
import { ProportyDataDeleteDialogComponent } from './proporty-data-delete-dialog.component';

@Component({
  selector: 'jhi-proporty-data',
  templateUrl: './proporty-data.component.html'
})
export class ProportyDataComponent implements OnInit, OnDestroy {
  proportyData?: IProportyData[];
  eventSubscriber?: Subscription;

  constructor(
    protected proportyDataService: ProportyDataService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.proportyDataService.query().subscribe((res: HttpResponse<IProportyData[]>) => {
      this.proportyData = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProportyData();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProportyData): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProportyData(): void {
    this.eventSubscriber = this.eventManager.subscribe('proportyDataListModification', () => this.loadAll());
  }

  delete(proportyData: IProportyData): void {
    const modalRef = this.modalService.open(ProportyDataDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.proportyData = proportyData;
  }
}
