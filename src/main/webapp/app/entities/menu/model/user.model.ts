export interface ICartUser {
  email?: string;
}

export class CartUser implements ICartUser {
  constructor(public email?: string) {}
}
