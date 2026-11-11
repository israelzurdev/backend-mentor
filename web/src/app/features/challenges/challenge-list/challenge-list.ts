import { Component, inject, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ChallengeService} from '../../../core/services/challenge';
import {ChallengeSummary} from '../../../core/models/challenge.model';

@Component({
  selector: 'app-challenge-list',
  standalone: true,
  imports: [RouterLink],
  templateUrl: 'challenge-list.html'
})
export class ChallengeList implements OnInit {
  private readonly challengeService = inject(ChallengeService);

  challenges = signal<ChallengeSummary[]>([]);
  isLoading = signal<boolean>(true);
  errorMessage = signal<string | null>(null);

  ngOnInit(): void {
    this.challengeService.getChallenges().subscribe({
      next: (data) => {
        this.challenges.set(data);
        this.isLoading.set(false);
      },
      error: (err) => {
        console.error('Error al conectar con la API', err);
        this.errorMessage.set('No se pudo conectar con el servidor backend');
        this.isLoading.set(false);
      }
    });
  }
}
