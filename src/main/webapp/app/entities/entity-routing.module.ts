import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'beer',
        data: { pageTitle: 'formosBeerApp.beer.home.title' },
        loadChildren: () => import('./beer/beer.module').then(m => m.BeerModule),
      },
      {
        path: 'client',
        data: { pageTitle: 'formosBeerApp.client.home.title' },
        loadChildren: () => import('./client/client.module').then(m => m.ClientModule),
      },
      {
        path: 'category',
        data: { pageTitle: 'formosBeerApp.category.home.title' },
        loadChildren: () => import('./category/category.module').then(m => m.CategoryModule),
      },
      {
        path: 'order-item',
        data: { pageTitle: 'formosBeerApp.orderItem.home.title' },
        loadChildren: () => import('./order-item/order-item.module').then(m => m.OrderItemModule),
      },
      {
        path: 'order',
        data: { pageTitle: 'formosBeerApp.order.home.title' },
        loadChildren: () => import('./order/order.module').then(m => m.OrderModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
