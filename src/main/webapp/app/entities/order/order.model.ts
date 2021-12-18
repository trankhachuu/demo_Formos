import * as dayjs from 'dayjs';
import { IOrderItem } from 'app/entities/order-item/order-item.model';
import { IClient } from 'app/entities/client/client.model';

export interface IOrder {
  id?: number;
  orderDate?: dayjs.Dayjs;
  shipDate?: dayjs.Dayjs;
  disCount?: number | null;
  total?: number;
  price?: number;
  quantity?: number;
  stautus?: boolean | null;
  orderItems?: IOrderItem[] | null;
  client?: IClient | null;
}

export class Order implements IOrder {
  constructor(
    public id?: number,
    public orderDate?: dayjs.Dayjs,
    public shipDate?: dayjs.Dayjs,
    public disCount?: number | null,
    public total?: number,
    public price?: number,
    public quantity?: number,
    public stautus?: boolean | null,
    public orderItems?: IOrderItem[] | null,
    public client?: IClient | null
  ) {
    this.stautus = this.stautus ?? false;
  }
}

export function getOrderIdentifier(order: IOrder): number | undefined {
  return order.id;
}
