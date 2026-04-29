import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  // On récupère le token avec le nom exact trouvé dans ton Local Storage
  const token = localStorage.getItem('preventhub_token'); 

  // Si on a un token, on clone la requête pour lui ajouter le header d'autorisation
  if (token) {
    const clonedRequest = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    // On envoie la requête modifiée avec le passeport (le token JWT)
    return next(clonedRequest);
  }

  // Si on n'a pas de token (ex: page de login), on envoie la requête normalement
  return next(req);
};