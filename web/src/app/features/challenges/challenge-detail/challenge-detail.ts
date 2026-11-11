import {Component, inject, input, OnInit, signal} from '@angular/core';
import {RouterLink} from '@angular/router';
import {ChallengeService} from '../../../core/services/challenge';
import {ChallengeDetail as ChallengeDetailModel} from '../../../core/models/challenge.model';

@Component({
  selector: 'app-challenge-detail',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './challenge-detail.html'
})
export class ChallengeDetail implements OnInit {
  // Recibe automáticamente el :slug desde la ruta gracias a withComponentInputBinding
  slug = input.required<string>();

  private readonly challengeService = inject(ChallengeService);

  challenge = signal<ChallengeDetailModel | null>(null);
  isLoading = signal<boolean>(true);
  errorMessage = signal<string | null>(null);

  ngOnInit(): void {
    this.challengeService.getChallengeBySlug(this.slug()).subscribe({
      next: (data) => {
        this.challenge.set(data);
        this.isLoading.set(false);
      },
      error: (err) => {
        console.error('Error al obtener el reto:', err);
        this.errorMessage.set('No se encontró el reto solicitado');
        this.isLoading.set(false);
      }
    });
  }
}
