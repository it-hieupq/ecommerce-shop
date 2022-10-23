import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {OrderComponent} from "./order/order.component";
import {LoginComponent} from "./login/login.component";
import {SignupComponent} from "./signup/signup.component";
import {CartListComponent} from "./cart-list/cart-list.component";
import {DashboardComponent} from "./admin/dashboard/dashboard.component";
import {AbillingListComponent} from "./admin/abilling-list/abilling-list.component";
import {BillingComponent} from "./billing/billing.component";
import {Error404Component} from "./error404/error404.component";
import {BillingListComponent} from "./billing-list/billing-list.component";
import {ForbiddenComponent} from "./forbidden/forbidden.component";
import {AuthUserService} from "./auth/auth-user.service";
import {AuthAdminService} from "./auth/auth-admin.service";
import {CategoryComponent} from "./category/category.component";
import {HomeComponent} from "./home/home.component";
import {AdminProductListComponent} from "./admin/admin-product-list/admin-product-list.component";
import {AdminUserListComponent} from "./admin/admin-user-list/admin-user-list.component";
import {AdminCategoryListComponent} from "./admin/admin-category-list/admin-category-list.component";
import {AdminCouponListComponent} from "./admin/admin-coupon-list/admin-coupon-list.component";
import {AdminCouponComponent} from "./admin/admin-coupon/admin-coupon.component";
import {AdminProductComponent} from "./admin/admin-product/admin-product.component";
import {MediaComponent} from "./admin/media/media.component";
import {OrderReportListComponent} from "./admin/order-report-list/order-report-list.component";
import {OrderReportItemComponent} from "./admin/order-report-item/order-report-item.component";
import {SearchComponent} from "./search/search.component";

const routes: Routes = [
  {path: 'admin/dashboard', component: DashboardComponent, canActivate: [AuthAdminService]},
  {path: 'admin/products', component: AdminProductListComponent, canActivate: [AuthAdminService]},
  {path: 'admin/products/:productId', component: AdminProductComponent, canActivate: [AuthAdminService]},
  {path: 'admin/products/add', component: AdminProductComponent, canActivate: [AuthAdminService]},
  {path: 'admin/users', component: AdminUserListComponent, canActivate: [AuthAdminService]},
  {path: 'admin/categories', component: AdminCategoryListComponent, canActivate: [AuthAdminService]},
  {path: 'admin/coupons', component: AdminCouponListComponent, canActivate: [AuthAdminService]},
  {path: 'admin/coupons/:couponId', component: AdminCouponComponent, canActivate: [AuthUserService]},
  {path: 'admin/coupons/add', component: AdminCouponComponent, canActivate: [AuthAdminService]},
  {path: 'admin/media', component: MediaComponent, canActivate: [AuthAdminService]},
  {path: 'admin/orders', component: OrderReportListComponent, canActivate: [AuthAdminService]},
  {path: 'admin/orders/:orderId', component: OrderReportItemComponent, canActivate: [AuthAdminService]},
  {path: 'admin/sales', component: AbillingListComponent, canActivate: [AuthAdminService]},

  {path: '', component: HomeComponent},
  {path: 'search', component: SearchComponent},
  {path: 'category/:categoryId', component: CategoryComponent},
  {path: 'cart', component: CartListComponent, canActivate: [AuthUserService]},
  {path: 'order', component: OrderComponent, canActivate: [AuthUserService]},
  {path: 'login', component: LoginComponent},
  {path: 'signup', component: SignupComponent},
  {path: 'billing/:orderId', component: BillingComponent, canActivate: [AuthUserService]},
  {path: 'billing', component: BillingListComponent, canActivate: [AuthUserService]},
  {path: 'forbidden', component: ForbiddenComponent},
  {path: '**', redirectTo: '/404'},
  {path: '404', component: Error404Component},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
