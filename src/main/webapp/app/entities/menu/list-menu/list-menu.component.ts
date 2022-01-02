import { Component, OnInit } from '@angular/core';
import { CartUser } from '../model/user.model';
import { Login } from './../../../login/login.model';
import { CartClient } from './../model/cart-user.model';
import { BeerMenuService } from './../service/beer-menu.service';

@Component({
  selector: 'jhi-list-menu',
  templateUrl: './list-menu.component.html',
  styleUrls: ['./list-menu.component.scss'],
})
export class ListMenuComponent implements OnInit {
  cartUser: CartUser;
  cartClient: CartClient;
  constructor(private beerMenuService: BeerMenuService) {
    this.cartUser = new CartUser();
    this.cartClient = new CartClient();
  }

  ngOnInit(): void {
    const dataLogin: Login = JSON.parse(localStorage.getItem('dataLogin') as string);
    this.cartUser.email = dataLogin.email;
    this.beerMenuService.getClient(this.cartUser.email).subscribe(res => {
      if (res) {
        this.cartClient = res.client;
        console.log(this.cartClient);
      }
    });
  }
}
