import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { CatchService } from '../../../core/services/catch.service';
import { UserService } from '../../../core/services/user.service';
import { Catch } from '../../../core/models/catch.model';

@Component({
  selector: 'app-catch-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './catch-list.component.html',
  styleUrl: './catch-list.component.css'
})
export class CatchListComponent implements OnInit {

  catches: Catch[] = [];
  pseudo: string = '';

  constructor(
    private catchService: CatchService,
    private userService: UserService
  ) {}

  ngOnInit() {
    const email = localStorage.getItem('email');
    if (email) {
      this.userService.getUserByEmail(email).subscribe({
        next: (user) => this.pseudo = user.pseudo,
        error: (err) => console.error('Erreur chargement profil', err)
      });
    }

    this.catchService.getAllCatches().subscribe({
      next: (data) => this.catches = data,
      error: (err) => console.error('Erreur chargement prises', err)
    });
  }
}
