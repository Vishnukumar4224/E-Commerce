import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { RegisterComponent } from './register/register.component';

export const routes: Routes = [
    {path: 'customer', loadChildren: ()=> import('./Customer/customer/customer.module').then(m=>m.CustomerModule)},
    {path: 'admin', loadChildren:()=> import('./admin/admin/admin.module').then(m=>m.AdminModule)},
    {path: 'login', component: LoginComponent},
    {path: 'signup', component: RegisterComponent}
];
