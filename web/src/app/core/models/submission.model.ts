export type SubmissionStatus = 'PENDING' | 'IN_REVIEW' | 'COMPLETED' | 'FAILED';

export interface CreateSubmissionRequest {
  githubRepoUrl: string;
  userIdentifier: string;
}

export interface SubmissionResponse {
  id: string;
  challengeId: string;
  userIdentifier: string;
  githubRepoUrl: string;
  status: SubmissionStatus;
  reviewNotes: string | null;
  createdAt: string;
}
