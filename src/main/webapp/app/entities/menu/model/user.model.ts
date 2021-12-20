export interface ICartUser {
  username?: string;
}

export class CartUser implements ICartUser {
  constructor(public username?: string) {}
}
