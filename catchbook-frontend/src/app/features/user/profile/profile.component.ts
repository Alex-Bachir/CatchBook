import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { UserService } from '../../../core/services/user.service';
import { CatchService } from '../../../core/services/catch.service';
import { CloudinaryService } from '../../../core/services/cloudinary.service';
import { AuthService } from '../../../core/services/auth.service';
import { User } from '../../../core/models/user.model';
import { Catch } from '../../../core/models/catch.model';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {

  user: User | null = null;
  catches: Catch[] = [];
  isUploadingPhoto: boolean = false;

  constructor(
    private userService: UserService,
    private catchService: CatchService,
    private cloudinaryService: CloudinaryService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    const decoded = this.authService.getDecodedToken();
    if (decoded) {
      this.userService.getUserById(decoded.userId).subscribe({
        next: (data) => {
          this.user = data;
          this.loadUserCatches(data.userId);
        },
        error: (err) => console.error('Erreur chargement profil', err)
      });
    }
  }

  loadUserCatches(userId: number) {
    this.catchService.getCatchesByUser(userId).subscribe({
      next: (data) => this.catches = data,
      error: (err) => console.error('Erreur chargement prises', err)
    });
  }

  async onPhotoSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files[0] && this.user) {
      this.isUploadingPhoto = true;
      const photoUrl = await this.cloudinaryService.uploadImage(input.files[0]);

      if (!photoUrl) {
        console.error('Erreur upload photo profil');
        this.isUploadingPhoto = false;
        return;
      }

      const updatedUser = { ...this.user, photoProfile: photoUrl };
      this.userService.updateUser(this.user.userId, updatedUser).subscribe({
        next: (data) => {
          this.user = data;
          this.isUploadingPhoto = false;
        },
        error: (err) => {
          console.error('Erreur mise à jour profil', err);
          this.isUploadingPhoto = false;
        }
      });
    }
  }
}
