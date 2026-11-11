import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./features/challenges/challenge-list/challenge-list')
        .then(m => m.ChallengeList)
  },
  {
    path: 'challenges/:slug',
    loadComponent: () =>
      import('./features/challenges/challenge-detail/challenge-detail')
        .then(m => m.ChallengeDetail)
  },
  {
    path: '**',
    redirectTo: ''
  }
];
