import { IOrder } from 'app/entities/order/order.model';
import { IBeer } from 'app/entities/beer/beer.model';

export interface IOrderItem {
  id?: number;
  orderId?: IOrder | null;
  beer?: IBeer | null;
}

export class OrderItem implements IOrderItem {
  constructor(public id?: number, public orderId?: IOrder | null, public beer?: IBeer | null) {}
}

export function getOrderItemIdentifier(orderItem: IOrderItem): number | undefined {
  return orderItem.id;
}
