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
    path: 'auth/callback',
    loadComponent: () => import('./features/auth/login/auth-callback.component')
      .then(m => m.AuthCallbackComponent)
  },
  {
    path: 'profile',
    loadComponent: () => import('./features/user/profile/profile.component')
      .then(m => m.ProfileComponent),
    canActivate: [authGuard]
  },
  {
    path: 'add-catch',
    loadComponent: () => import('./features/catch/catch-list/add-catch/add-catch.component')
      .then(m => m.AddCatchComponent),
    canActivate: [authGuard]
  },
];
