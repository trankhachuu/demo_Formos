import * as dayjs from 'dayjs';
import { IOrderItem } from 'app/entities/order-item/order-item.model';
import { IClient } from 'app/entities/client/client.model';

export interface ICartOrder {
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
  image?: string | null;
  manufacturer?: string | null;
  beerName?: string | null;
  description?: string | null;
  beerId?: number | null;
}

export class CartOrder implements ICartOrder {
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
    public client?: IClient | null,
    public image?: string | null,
    public manufacturer?: string | null,
    public beerName?: string | null,
    public description?: string | null,
    public beerId?: number | null
  ) {
    this.stautus = this.stautus ?? false;
  }
}
