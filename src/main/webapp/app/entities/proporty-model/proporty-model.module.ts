import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BusinessDataSharedModule } from 'app/shared/shared.module';
import { ProportyModelComponent } from './proporty-model.component';
import { ProportyModelDetailComponent } from './proporty-model-detail.component';
import { ProportyModelUpdateComponent } from './proporty-model-update.component';
import { ProportyModelDeleteDialogComponent } from './proporty-model-delete-dialog.component';
import { proportyModelRoute } from './proporty-model.route';

@NgModule({
  imports: [BusinessDataSharedModule, RouterModule.forChild(proportyModelRoute)],
  declarations: [ProportyModelComponent, ProportyModelDetailComponent, ProportyModelUpdateComponent, ProportyModelDeleteDialogComponent],
  entryComponents: [ProportyModelDeleteDialogComponent]
})
export class BusinessDataProportyModelModule {}
