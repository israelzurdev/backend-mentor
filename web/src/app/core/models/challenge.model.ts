export type Difficulty = 'JUNIOR' | 'MID' | 'SENIOR';
export type Category = 'SECURITY' | 'PERFORMANCE' | 'DATABASE' | 'SYSTEM_DESING';

export interface ChallengeSummary {
  id: string;
  title: string;
  slug: string;
  difficulty: Difficulty;
  category: Category;
}

export interface ChallengeDetail {
  id: string;
  title: string;
  slug: string;
  descriptionMd: string;
  difficulty: Difficulty;
  category: Category;
  createdAt: string;
}
