import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from '../../../core/services/user.service';
import { User } from '../../../core/models/user.model';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {

  user: User | null = null;

  constructor(private userService: UserService) {}

  ngOnInit() {
    const userId = 1; // Plus tard on récupèrera l'id depuis le token JWT
    this.userService.getUserById(userId).subscribe({
      next: (data) => this.user = data,
      error: (err) => console.error('Erreur chargement profil', err)
    });
  }
}
