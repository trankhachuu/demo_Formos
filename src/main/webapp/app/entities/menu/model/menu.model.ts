import { BeerTemplate } from './beer-template.model';

export interface IBeerMenu {
    dataRequests: BeerTemplate[];
  }
  
  export class BeerMenu implements IBeerMenu {
    constructor(
      public dataRequests: BeerTemplate[],
    ) {
        this.dataRequests = dataRequests;
    }
  }
  