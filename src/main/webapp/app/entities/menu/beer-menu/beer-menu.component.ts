import { Component, OnInit } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { Login } from '../../../login/login.model';
import { BeerTemplate } from '../model/beer-template.model';
import { BeerMenu } from '../model/menu.model';
import { BeerMenuService } from './../service/beer-menu.service';

@Component({
  selector: 'jhi-beer-menu',
  templateUrl: './beer-menu.component.html',
  styleUrls: ['./beer-menu.component.scss'],
})
export class BeerMenuComponent implements OnInit {
  listBeer: BeerTemplate[] = [];
  beerItemList: BeerTemplate[] = [];
  beerMenu: BeerMenu;
  clientId?: number;
  constructor(private beerService: BeerMenuService) {
    this.beerMenu = new BeerMenu([]);
    this.clientId = undefined;
  }

  ngOnInit(): void {
    this.getALlBeer();
  }

  showData(): void {
    const dataLogin: Login = JSON.parse(localStorage.getItem('dataLogin') as string);
    const username = dataLogin.username ? dataLogin.username : '';
    this.beerService.getALlBeer('/get-all-beer/' + username).subscribe(
      res => {
        this.listBeer = res;
        this.listBeer.forEach(item => {
          item.username = dataLogin.username;
          item.password = dataLogin.password;
          if (item.clientID) {
            this.clientId = item.clientID;
          }
        });
      },
      error => {
        this.handleError(error);
      }
    );
  }

  getALlBeer(): void {
    this.showData();

    this.beerService.getALlBeer('/get-all-beer').subscribe(
      res => {
        this.beerItemList = res;
      },
      error => {
        this.handleError(error);
      }
    );
  }

  handleError(error: any): Observable<never> {
    const exception = error.error;
    return throwError(exception.message);
  }

  addtocart(item: BeerTemplate): void {
    this.getTotalPrice('plus', item);
  }

  removetocart(item: BeerTemplate): void {
    this.getTotalPrice('minus', item);
  }

  clearData(): void {
    if (this.clientId) {
      this.beerService.deleteClient(this.clientId);
    }
    this.showData();
  }

  getTotalPrice(data: string, item: BeerTemplate): void {
    let dem = 1;
    let price = 0;
    this.beerItemList.forEach(e => {
      if (e.id === item.id) {
        price = e.price;
      }
    });
    this.listBeer.forEach(a => {
      // check data total. if total undefined ? 0 : total
      if (!a.total && a.total !== 0) {
        a.total = 0;
      }

      if (a.id === item.id) {
        if (data === 'plus') {
          if (a.price <= 0) {
            return;
          }

          if (a.total >= 0 && a.price >= 0 && a.total < a.quantity) {
            a.total += dem;
            if (a.price === price && a.total === 1) {
              a.total = 1;
              return;
            }
            a.price += price;

            if (a.total <= 0) {
              a.price = price;
            }
          }
        } else if (data === 'minus') {
          if (a.price <= 0) {
            return;
          }

          if (a.total >= 0 && a.price >= 0) {
            a.total -= dem;
            a.price -= price;

            if (a.total <= 0) {
              a.total = 0;
              a.price = price;
            }
          }
        }
        dem++;
      }
    });
  }

  saveMenuData(): void {
    this.listBeer.forEach(item => {
      if (item.total && item.total !== 0) {
        this.beerMenu.dataRequests.push(item);
      }
    });
    this.beerService.saveMenu(this.beerMenu).subscribe(
      res => {
        this.showData();
        window.alert('thanh cong');
      },
      error => {
        this.handleError(error);
      }
    );
  }
}
