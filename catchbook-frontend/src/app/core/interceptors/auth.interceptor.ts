import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  // Ne pas ajouter le token sur les routes d'authentification
  if (req.url.includes('/api/auth/')) {
    return next(req);
  }

  const token = localStorage.getItem('token');

  if (token) {
    const cloned = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${token}`)
    });
    return next(cloned);
  }

  return next(req);
};
