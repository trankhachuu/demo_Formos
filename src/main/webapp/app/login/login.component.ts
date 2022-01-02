import { Component, ViewChild, OnInit, AfterViewInit, ElementRef } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { LoginService } from 'app/login/login.service';
import { AccountService } from 'app/core/auth/account.service';
import { Login } from './login.model';

@Component({
  selector: 'jhi-login',
  templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit, AfterViewInit {
  @ViewChild('email', { static: false })
  email!: ElementRef;
  dataLogin!: Login;

  authenticationError = false;

  loginForm = this.fb.group({
    email: ['', [Validators.minLength(5), Validators.maxLength(254), Validators.email]],
    password: [null, [Validators.required]],
    rememberMe: [false],
  });

  constructor(
    private accountService: AccountService,
    private loginService: LoginService,
    private router: Router,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    // if already authenticated then navigate to home page
    this.accountService.identity().subscribe(() => {
      if (this.accountService.isAuthenticated()) {
        this.router.navigate(['']);
      }
    });
  }

  ngAfterViewInit(): void {
    this.email.nativeElement.focus();
  }

  login(): void {
    this.loginService.login(this.getLogin()).subscribe(
      () => {
        this.authenticationError = false;
        if (!this.router.getCurrentNavigation()) {
          // There were no routing during login (eg from navigationToStoredUrl)
          this.router.navigate(['']);
        }
      },
      () => (this.authenticationError = true)
    );
  }

  getLogin(): Login {
    this.dataLogin = {
      email: this.loginForm.get('email')!.value,
      password: this.loginForm.get('password')!.value,
      rememberMe: this.loginForm.get('rememberMe')!.value,
    };
    localStorage.setItem('dataLogin', JSON.stringify(this.dataLogin));
    return this.dataLogin;
  }
}
