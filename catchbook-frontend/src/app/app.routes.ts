import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'catches',
    pathMatch: 'full'
  },
  {
    path: 'login',
    loadComponent: () => import('./features/auth/login/login.component')
      .then(m => m.LoginComponent)
  },
  {
    path: 'catches',
    loadComponent: () => import('./features/catch/catch-list/catch-list.component')
      .then(m => m.CatchListComponent),
    canActivate: [authGuard]
  },
  {
    path: 'profile',
    loadComponent: () => import('./features/user/profile/profile.component')
      .then(m => m.ProfileComponent),
    canActivate: [authGuard]
  }
];
