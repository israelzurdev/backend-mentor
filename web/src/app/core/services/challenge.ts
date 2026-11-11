import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable} from 'rxjs';
import { ChallengeSummary, Difficulty } from '../models/challenge.model';

@Injectable({
  providedIn: 'root'
})
export class ChallengeService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/api/v1/challenges';

  getChallenges(difficulty?: Difficulty): Observable<ChallengeSummary[]> {
    let params = new HttpParams();
    if (difficulty) {
      params = params.set('difficulty', difficulty);
    }
    return this.http.get<ChallengeSummary[]>(this.apiUrl, { params });
  }

  getChallengeBySlug(slug: string): Observable<ChallengeSummary> {
    return this.http.get<ChallengeSummary>(`${this.apiUrl}/${slug}`);
  }

}
