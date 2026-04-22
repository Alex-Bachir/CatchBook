import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  email: string = '';
  password: string = '';

  constructor(private router: Router) {}

  onLogin() {
    // Pour l'instant on simule une connexion
    // Plus tard on appellera l'auth-service
    if (this.email && this.password) {
      localStorage.setItem('token', 'fake-token');
      this.router.navigate(['/catches']);
    }
  }
}
