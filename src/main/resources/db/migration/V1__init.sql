CREATE TYPE lift_type AS ENUM ('SQUAT', 'BENCH', 'DEADLIFT');
CREATE TYPE session_status AS ENUM ('PENDING', 'PROCESSING', 'COMPLETED', 'FAILED');

CREATE TABLE lift_session (
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id       UUID NOT NULL,
    lift_type     lift_type NOT NULL,
    video_path    TEXT NOT NULL,
    status        session_status NOT NULL DEFAULT 'PENDING',
    error_message TEXT,
    created_at    TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    completed_at  TIMESTAMPTZ
);

CREATE INDEX idx_session_user ON lift_session(user_id, created_at DESC);
CREATE INDEX idx_session_pending ON lift_session(status)
    WHERE status IN ('PENDING', 'PROCESSING');

CREATE TABLE pose_frame (
    id           BIGSERIAL PRIMARY KEY,
    session_id   UUID NOT NULL REFERENCES lift_session(id) ON DELETE CASCADE,
    frame_index  INT NOT NULL,
    timestamp_ms INT NOT NULL,
    keypoints    JSONB NOT NULL,
    UNIQUE(session_id, frame_index)
);

CREATE INDEX idx_frame_session ON pose_frame(session_id, frame_index);

CREATE TABLE rep_analysis (
    id            BIGSERIAL PRIMARY KEY,
    session_id    UUID NOT NULL REFERENCES lift_session(id) ON DELETE CASCADE,
    rep_number    INT NOT NULL,
    start_frame   INT NOT NULL,
    end_frame     INT NOT NULL,
    metrics       JSONB NOT NULL,
    feedback_text TEXT,
    UNIQUE(session_id, rep_number)
);

CREATE INDEX idx_rep_session ON rep_analysis(session_id, rep_number);