import { Component, inject, OnInit, signal } from '@angular/core';
import { ChallengeService } from './core/services/challenge';
import { ChallengeSummary } from './core/models/challenge.model';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  private readonly challengeService = inject(ChallengeService);

  challenges = signal<ChallengeSummary[]>([]);
  isLoading = signal<boolean>(true);
  errorMessage = signal<string | null>(null);

  ngOnInit(): void {
    this.loadChallenges();
  }

  loadChallenges(): void {
    this.isLoading.set(true);
    this.challengeService.getChallenges().subscribe({
      next: (data) => {
        this.challenges.set(data);
        this.isLoading.set(false);
      },
      error: (err) => {
        console.error('Error al conectar con la API:', err);
        this.errorMessage.set('No se pudo conectar con el servidor backend');
        this.isLoading.set(false);
      }
    });
  }

}
