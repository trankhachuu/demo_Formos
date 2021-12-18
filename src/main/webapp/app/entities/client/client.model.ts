import { IUser } from 'app/entities/user/user.model';
import { IOrder } from 'app/entities/order/order.model';

export interface IClient {
  id?: number;
  user?: IUser | null;
  orders?: IOrder[] | null;
}

export class Client implements IClient {
  constructor(public id?: number, public user?: IUser | null, public orders?: IOrder[] | null) {}
}

export function getClientIdentifier(client: IClient): number | undefined {
  return client.id;
}
