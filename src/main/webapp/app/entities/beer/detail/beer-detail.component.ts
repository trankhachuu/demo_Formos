import { AccountService } from './../../../core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBeer } from '../beer.model';

@Component({
  selector: 'jhi-beer-detail',
  templateUrl: './beer-detail.component.html',
})
export class BeerDetailComponent implements OnInit {
  beer: IBeer | null = null;
  currentAccount: Account | null = null;

  constructor(protected activatedRoute: ActivatedRoute, protected accountService: AccountService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ beer }) => {
      this.beer = beer;
    });
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
  }

  previousState(): void {
    window.history.back();
  }
}
