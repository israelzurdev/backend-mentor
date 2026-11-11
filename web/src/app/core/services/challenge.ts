import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ChallengeDetail, ChallengeSummary, Difficulty } from '../models/challenge.model';

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

  getChallengeBySlug(slug: string): Observable<ChallengeDetail> {
    return this.http.get<ChallengeDetail>(`${this.apiUrl}/${slug}`);
  }
}
