import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { CatchService } from '../../../core/services/catch.service';
import { UserService } from '../../../core/services/user.service';
import { AuthService } from '../../../core/services/auth.service';
import { Catch } from '../../../core/models/catch.model';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

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
  userMap: { [userId: number]: string } = {};

  constructor(
    private catchService: CatchService,
    private userService: UserService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    const decoded = this.authService.getDecodedToken();
    if (decoded) {
      this.userService.getUserById(decoded.userId).subscribe({
        next: (user) => this.pseudo = user.pseudo ?? user.firstName,
        error: (err) => console.error('Erreur chargement profil', err)
      });
    }

    this.catchService.getAllCatches().subscribe({
      next: (data) => {
        this.catches = data;
        this.loadUserPseudos(data);
      },
      error: (err) => console.error('Erreur chargement prises', err)
    });
  }

  loadUserPseudos(catches: Catch[]) {
    const uniqueUserIds = [...new Set(catches.map(c => c.userId))];

    const requests = uniqueUserIds.map(id =>
      this.userService.getUserById(id).pipe(catchError(() => of(null)))
    );

    forkJoin(requests).subscribe(users => {
      const newMap: { [userId: number]: string } = {};
      users.forEach((user, index) => {
        if (user) {
          newMap[uniqueUserIds[index]] = user.pseudo ?? user.firstName;
        }
      });
      this.userMap = newMap;
    });
  }
}
