import { ICartOrder } from './cart-order.model';
import { IUser } from 'app/entities/user/user.model';

export interface ICartClient {
  id?: number;
  user?: IUser | null;
  orders?: ICartOrder[] | null;
}

export class CartClient implements ICartClient {
  constructor(public id?: number, public user?: IUser | null, public orders?: ICartOrder[] | null) {}
}
