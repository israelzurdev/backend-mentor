import { Component, inject, input, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { ChallengeService } from '../../../core/services/challenge';
import { SubmissionService } from '../../../core/services/submission';
import { ChallengeDetail as ChallengeDetailModel } from '../../../core/models/challenge.model';
import { SubmissionResponse } from '../../../core/models/submission.model';

@Component({
  selector: 'app-challenge-detail',
  standalone: true,
  imports: [RouterLink, FormsModule, DatePipe],
  templateUrl: './challenge-detail.html'
})
export class ChallengeDetail implements OnInit {
  slug = input.required<string>();

  private readonly challengeService = inject(ChallengeService);
  private readonly submissionService = inject(SubmissionService);

  // Estados del reto
  challenge = signal<ChallengeDetailModel | null>(null);
  isLoading = signal<boolean>(true);
  errorMessage = signal<string | null>(null);

  // Estados de envíos
  submissions = signal<SubmissionResponse[]>([]);
  repoUrl = signal<string>('');
  isSubmitting = signal<boolean>(false);
  submitError = signal<string | null>(null);
  submitSuccess = signal<boolean>(false);

  ngOnInit(): void {
    const currentSlug = this.slug();
    this.loadChallenge(currentSlug);
    this.loadSubmissions(currentSlug);
  }

  loadChallenge(slug: string): void {
    this.challengeService.getChallengeBySlug(slug).subscribe({
      next: (data) => {
        this.challenge.set(data);
        this.isLoading.set(false);
      },
      error: (err) => {
        console.error('Error al cargar el reto:', err);
        this.errorMessage.set('No se encontró el reto solicitado');
        this.isLoading.set(false);
      }
    });
  }

  loadSubmissions(slug: string): void {
    this.submissionService.getSubmissionsByChallenge(slug).subscribe({
      next: (data) => this.submissions.set(data),
      error: (err) => console.error('Error al cargar entregas:', err)
    });
  }

  onSubmit(): void {
    if (!this.repoUrl().trim()) return;

    this.isSubmitting.set(true);
    this.submitError.set(null);
    this.submitSuccess.set(false);

    this.submissionService.createSubmission(this.slug(), {
      githubRepoUrl: this.repoUrl().trim(),
      userIdentifier: 'isra_demo' // Mock mientras integramos Clerk
    }).subscribe({
      next: (newSubmission) => {
        // Añadimos la nueva entrega al inicio de la lista de forma inmutable
        this.submissions.update((prev) => [newSubmission, ...prev]);
        this.repoUrl.set('');
        this.isSubmitting.set(false);
        this.submitSuccess.set(true);
      },
      error: (err) => {
        this.isSubmitting.set(false);
        const msg = err.error?.message || 'Error al registrar la entrega. Revisa los datos.';
        this.submitError.set(msg);
      }
    });
  }
}
