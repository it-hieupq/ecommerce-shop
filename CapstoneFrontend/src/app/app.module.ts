import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './common/header/header.component';
import { FooterComponent } from './common/footer/footer.component';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { CartComponent } from './cart/cart.component';
import {HttpClientModule} from "@angular/common/http";
import { OrderComponent } from './order/order.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { CartListComponent } from './cart-list/cart-list.component';
import { OrderListComponent } from './order-list/order-list.component';
import { OrderDetailsComponent } from './order-details/order-details.component';
import { DashboardComponent } from './admin/dashboard/dashboard.component';
import { BillingComponent } from './billing/billing.component';
import { BillingListComponent } from './billing-list/billing-list.component';
import { AbillingListComponent } from './admin/abilling-list/abilling-list.component';
import { AbillingComponent } from './admin/abilling/abilling.component';
import { Error404Component } from './error404/error404.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { CategoryComponent } from './category/category.component';
import { ProductItemComponent } from './product-item/product-item.component';
import { HomeComponent } from './home/home.component';
import { AdminProductComponent } from './admin/admin-product/admin-product.component';
import { AdminProductListComponent } from './admin/admin-product-list/admin-product-list.component';
import { AdminUserListComponent } from './admin/admin-user-list/admin-user-list.component';
import { AdminCategoryListComponent } from './admin/admin-category-list/admin-category-list.component';
import { AdminCouponListComponent } from './admin/admin-coupon-list/admin-coupon-list.component';
import { AdminCouponComponent } from './admin/admin-coupon/admin-coupon.component';
import { MediaComponent } from './admin/media/media.component';
import { SaleReportListComponent } from './admin/sale-report-list/sale-report-list.component';
import { SaleReportItemComponent } from './admin/sale-report-item/sale-report-item.component';
import { OrderReportListComponent } from './admin/order-report-list/order-report-list.component';
import { OrderReportItemComponent } from './admin/order-report-item/order-report-item.component';
import { SearchComponent } from './search/search.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    AdminProductListComponent,
    LoginComponent,
    SignupComponent,
    CartComponent,
    OrderComponent,
    CartListComponent,
    OrderListComponent,
    OrderDetailsComponent,
    DashboardComponent,
    BillingComponent,
    BillingListComponent,
    AbillingListComponent,
    AbillingComponent,
    Error404Component,
    ForbiddenComponent,
    CategoryComponent,
    ProductItemComponent,
    HomeComponent,
    AdminProductComponent,
    AdminProductListComponent,
    AdminUserListComponent,
    AdminCategoryListComponent,
    AdminCouponListComponent,
    AdminCouponComponent,
    MediaComponent,
    SaleReportListComponent,
    SaleReportItemComponent,
    OrderReportListComponent,
    OrderReportItemComponent,
    SearchComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
