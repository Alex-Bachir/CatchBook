import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CatchService } from '../../../../core/services/catch.service';
import { UserService } from '../../../../core/services/user.service';
import { CloudinaryService } from '../../../../core/services/cloudinary.service';
import { Catch } from '../../../../core/models/catch.model';

@Component({
  selector: 'app-add-catch',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './add-catch.component.html',
  styleUrl: './add-catch.component.css'
})
export class AddCatchComponent {

  fishName: string = '';
  fishWeight: number | null = null;
  size: number | null = null;
  catchDate: string = '';
  spotPublic: boolean = false;

  selectedFile: File | null = null;
  previewUrl: string | null = null;
  isUploading: boolean = false;
  isLoading: boolean = false;
  errorMessage: string = '';

  constructor(
    private catchService: CatchService,
    private userService: UserService,
    private cloudinaryService: CloudinaryService,
    private router: Router
  ) {}

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files[0]) {
      this.selectedFile = input.files[0];
      const reader = new FileReader();
      reader.onload = (e) => {
        this.previewUrl = e.target?.result as string;
      };
      reader.readAsDataURL(this.selectedFile);
    }
  }

  async onSubmit() {
    if (!this.fishName || !this.fishWeight || !this.size || !this.catchDate) {
      this.errorMessage = 'Veuillez remplir tous les champs obligatoires.';
      return;
    }

    const email = localStorage.getItem('email');
    if (!email) {
      this.errorMessage = 'Utilisateur non connecté.';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';

    this.userService.getUserByEmail(email).subscribe({
      next: async (user) => {
        let photoUrl: string | null = null;
        if (this.selectedFile) {
          this.isUploading = true;
          photoUrl = await this.cloudinaryService.uploadImage(this.selectedFile);
          this.isUploading = false;
          if (!photoUrl) {
            this.errorMessage = "Erreur lors de l'upload de la photo.";
            this.isLoading = false;
            return;
          }
        }

        const newCatch: Partial<Catch> = {
          userId: user.userId,
          fishName: this.fishName,
          fishWeight: this.fishWeight!,
          size: this.size!,
          catchDate: new Date(this.catchDate),
          spotPublic: this.spotPublic,
          photoUrl: photoUrl ?? undefined,
        };

        this.catchService.saveCatch(newCatch as Catch).subscribe({
          next: () => this.router.navigate(['/catches']),
          error: (err) => {
            console.error(err);
            this.errorMessage = "Erreur lors de l'enregistrement.";
            this.isLoading = false;
          }
        });
      },
      error: () => {
        this.errorMessage = 'Impossible de récupérer le profil utilisateur.';
        this.isLoading = false;
      }
    });
  }
}
