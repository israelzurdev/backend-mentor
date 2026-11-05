CREATE TABLE users
(
    id                UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    username          VARCHAR(50)  NOT NULL UNIQUE,
    email             VARCHAR(100) NOT NULL UNIQUE,
    password_hash     VARCHAR(255) NOT NULL,
    github_username   VARCHAR(100),
    role              VARCHAR(20)  NOT NULL    DEFAULT 'USER',
    reputation_points INT          NOT NULL    DEFAULT 0,
    created_at        TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE challenges
(
    id             UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    slug           VARCHAR(100) NOT NULL UNIQUE,
    title          VARCHAR(150) NOT NULL,
    description_md TEXT         NOT NULL,
    difficulty     VARCHAR(20)  NOT NULL,
    category       VARCHAR(50)  NOT NULL,
    active         BOOLEAN      NOT NULL    DEFAULT TRUE,
    created_at     TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE challenge_test_cases
(
    id                 UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    challenge_id       UUID                                               NOT NULL,
    execution_order    INT                                                NOT NULL,
    name               VARCHAR(100)                                       NOT NULL,
    http_method        VARCHAR(10)                                        NOT NULL,
    endpoint_path      VARCHAR(255)                                       NOT NULL,
    request_body_json  TEXT,
    expected_status    INT                                                NOT NULL,
    expected_json_keys TEXT,
    created_at         TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,

    CONSTRAINT fk_test_cases_challenge FOREIGN KEY (challenge_id) REFERENCES challenges (id) ON DELETE CASCADE
);

CREATE TABLE submissions
(
    id                 UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    user_id            UUID         NOT NULL,
    challenge_id       UUID         NOT NULL,
    repo_url           VARCHAR(255) NOT NULL,
    live_api_url       VARCHAR(255),
    tech_stack         VARCHAR(100),
    architecture_notes TEXT,
    passed_tests_count INT          NOT NULL    DEFAULT 0,
    total_tests_count  INT          NOT NULL    DEFAULT 0,
    status             VARCHAR(20)  NOT NULL    DEFAULT 'PARTIAL',
    created_at         TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,

    CONSTRAINT fk_submissions_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_submissions_challenge FOREIGN KEY (challenge_id) REFERENCES challenges (id) ON DELETE CASCADE
);

CREATE TABLE submission_test_results
(
    id               UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    submission_id    UUID                                               NOT NULL,
    test_case_id     UUID                                               NOT NULL,
    passed           BOOLEAN                                            NOT NULL,
    actual_status    INT,
    response_time_ms INT,
    error_message    TEXT,
    created_at       TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,

    CONSTRAINT fk_results_submission FOREIGN KEY (submission_id) REFERENCES submissions (id) ON DELETE CASCADE,
    CONSTRAINT fk_results_test_case FOREIGN KEY (test_case_id) REFERENCES challenge_test_cases (id) ON DELETE CASCADE
);

CREATE TABLE code_reviews
(
    id            UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    submission_id UUID        NOT NULL,
    author_id     UUID        NOT NULL,
    category      VARCHAR(50) NOT NULL,
    content_md    TEXT        NOT NULL,
    upvotes_count INT         NOT NULL     DEFAULT 0,
    version       BIGINT      NOT NULL     DEFAULT 0,
    created_at    TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,

    CONSTRAINT fk_reviews_submission FOREIGN KEY (submission_id) REFERENCES submissions (id) ON DELETE CASCADE,
    CONSTRAINT fk_reviews_author FOREIGN KEY (author_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE review_upvotes
(
    id         UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    review_id  UUID                                               NOT NULL,
    user_id    UUID                                               NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,

    CONSTRAINT fk_upvotes_review FOREIGN KEY (review_id) REFERENCES code_reviews (id) ON DELETE CASCADE,
    CONSTRAINT fk_upvotes_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT uq_review_user UNIQUE (review_id, user_id)
);
