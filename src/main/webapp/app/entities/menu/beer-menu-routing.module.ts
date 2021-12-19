import { UserRouteAccessService } from '../../core/auth/user-route-access.service';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BeerMenuComponent } from './beer-menu/beer-menu.component';
import { ListMenuComponent } from './list-menu/list-menu.component';

const menuRoute: Routes = [
  {
    path: '',
    component: BeerMenuComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'cart',
    component: ListMenuComponent,
    canActivate: [UserRouteAccessService],
  }
];

@NgModule({
  imports: [RouterModule.forChild(menuRoute)],
  exports: [RouterModule],
})
export class MenuRoutingModule {}
