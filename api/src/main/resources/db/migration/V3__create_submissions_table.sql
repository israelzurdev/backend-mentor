-- Elimina residuos previos para garantizar un esquema limpio
DROP TABLE IF EXISTS submissions CASCADE;

CREATE TABLE submissions
(
    id              UUID PRIMARY KEY,
    challenge_id    UUID                     NOT NULL,
    user_identifier VARCHAR(100)             NOT NULL, -- Soporta ID de Clerk (user_xxx), GitHub username o 'guest'
    github_repo_url VARCHAR(255)             NOT NULL,
    status          VARCHAR(30)              NOT NULL DEFAULT 'PENDING',
    review_notes    TEXT,                              -- Feedback técnico o logs de corrección
    created_at      TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at      TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT fk_submissions_challenge
        FOREIGN KEY (challenge_id)
            REFERENCES challenges (id)
            ON DELETE CASCADE
);

CREATE INDEX idx_submissions_challenge_id ON submissions (challenge_id);
CREATE INDEX idx_submissions_user_identifier ON submissions (user_identifier);
