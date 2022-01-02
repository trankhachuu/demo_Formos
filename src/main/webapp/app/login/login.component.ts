import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/login/login.service';
import { Login } from './login.model';

@Component({
  selector: 'jhi-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
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
    if (this.canSave(this.loginForm, document)) {
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

  /*
  check formValidity before save data
  */
  public canSave(formdata: FormGroup, document: Document): boolean {
    if (formdata && formdata.dirty && formdata.controls) {
      const listControl = Object.keys(formdata.controls);

      for (let i = 0; i < listControl.length; i++) {
        const control: AbstractControl = formdata.controls[listControl[i]];
        const controlErr = control.errors;
        if (controlErr != null) {
          const element = document.getElementById(listControl[i]);
          if (element) {
            element.focus();
          }
          return false;
        }
      }
    }
    return true;
  }
}
