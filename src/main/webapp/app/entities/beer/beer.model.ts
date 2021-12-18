import { IOrderItem } from 'app/entities/order-item/order-item.model';
import { ICategory } from 'app/entities/category/category.model';

export interface IBeer {
  id?: number;
  manufacturer?: string;
  name?: string;
  country?: string | null;
  image?: string | null;
  price?: number | null;
  description?: string | null;
  quantity?: number | null;
  archive?: boolean | null;
  orderItems?: IOrderItem[] | null;
  category?: ICategory | null;
}

export class Beer implements IBeer {
  constructor(
    public id?: number,
    public manufacturer?: string,
    public name?: string,
    public country?: string | null,
    public image?: string | null,
    public price?: number | null,
    public description?: string | null,
    public quantity?: number | null,
    public archive?: boolean | null,
    public orderItems?: IOrderItem[] | null,
    public category?: ICategory | null
  ) {
    this.archive = this.archive ?? false;
  }
}

export function getBeerIdentifier(beer: IBeer): number | undefined {
  return beer.id;
}
