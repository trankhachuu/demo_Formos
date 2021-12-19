import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BeerMenuComponent } from './beer-menu.component';

describe('BeerMenuComponent', () => {
  let component: BeerMenuComponent;
  let fixture: ComponentFixture<BeerMenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BeerMenuComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BeerMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
