import { IOrderItem } from 'app/entities/order-item/order-item.model';
import { ICategory } from 'app/entities/category/category.model';

export interface IBeerTemplate {
  id: number;
  manufacturer?: string;
  name?: string;
  country?: string | null;
  image?: string | null;
  price: number;
  description?: string | null;
  quantity: number;
  archive?: boolean | null;
  orderItem?: IOrderItem | null;
  categories?: ICategory[] | null;
  total: number;
  username?: string | null;
  password?: string | null;
}

export class BeerTemplate implements IBeerTemplate {
  constructor(
    public id: number = 0,
    public manufacturer?: string,
    public name?: string,
    public country?: string | null,
    public image?: string | null,
    public price: number = 0,
    public description?: string | null,
    public quantity: number = 0,
    public archive?: boolean | null,
    public orderItem?: IOrderItem | null,
    public categories?: ICategory[] | null,
    public total: number = 0,
    public username?: string | null,
    public password?: string | null
  ) {
    this.archive = this.archive ?? false;
  }
}
