import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CatchService } from '../../../core/services/catch.service';
import { Catch } from '../../../core/models/catch.model';

@Component({
  selector: 'app-catch-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './catch-list.component.html',
  styleUrl: './catch-list.component.css'
})
export class CatchListComponent implements OnInit {

  catches: Catch[] = [];

  constructor(private catchService: CatchService) {}

  ngOnInit() {
    this.catchService.getAllCatches().subscribe({
      next: (data) => this.catches = data,
      error: (err) => console.error('Erreur chargement prises', err)
    });
  }
}
