import { IBeer } from 'app/entities/beer/beer.model';

export interface ICategory {
  id?: number;
  name?: string;
  stautus?: boolean | null;
  beers?: IBeer[] | null;
}

export class Category implements ICategory {
  constructor(public id?: number, public name?: string, public stautus?: boolean | null, public beers?: IBeer[] | null) {
    this.stautus = this.stautus ?? false;
  }
}

export function getCategoryIdentifier(category: ICategory): number | undefined {
  return category.id;
}
