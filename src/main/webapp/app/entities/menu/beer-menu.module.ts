import { MenuRoutingModule } from './beer-menu-routing.module';
import { NgModule } from '@angular/core';
import { SharedModule } from '../../shared/shared.module';
import { BeerMenuComponent } from './beer-menu/beer-menu.component';
import { ListMenuComponent } from './list-menu/list-menu.component';
import { OrderstatusComponent } from './orderstatus/orderstatus.component';


@NgModule({
  declarations: [BeerMenuComponent, ListMenuComponent, OrderstatusComponent],
  imports: [
  SharedModule,
    MenuRoutingModule
  ]
})
export class BeerMenuModule { }
