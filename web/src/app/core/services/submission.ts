import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {CreateSubmissionRequest, SubmissionResponse} from '../models/submission.model';

@Injectable({
  providedIn: 'root'
})
export class SubmissionService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8080/api/v1/challenges';

  createSubmission(challengeSlug: string, request: CreateSubmissionRequest): Observable<SubmissionResponse> {
    return this.http.post<SubmissionResponse>(
      `${this.baseUrl}/${challengeSlug}/submissions`,
      request
    );
  }

  getSubmissionsByChallenge(challengeSlug: string): Observable<SubmissionResponse[]> {
    return this.http.get<SubmissionResponse[]>(
      `${this.baseUrl}/${challengeSlug}/submissions`
    );
  }
}
