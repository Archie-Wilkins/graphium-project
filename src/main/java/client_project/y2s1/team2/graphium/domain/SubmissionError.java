package client_project.y2s1.team2.graphium.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SubmissionError {
    @Getter
    private boolean errored;        // Did it not pass or succeed
    @Getter
    private String error;           // It's short error, maybe the main title to check against
    @Getter
    private String niceError;       // It's longer error message to explain the issue, possibly display to users

    private boolean hasErrorMessage;


    public SubmissionError(boolean didError, String aError, String aNiceError) {
        this.errored = didError;
        this.error = aError;
        this.niceError = aNiceError;
        this.hasErrorMessage = true;
    }
    public SubmissionError(boolean didError, String aError) {
        this.errored = didError;
        this.error = aError;
        this.niceError = aError;
        this.hasErrorMessage = true;
    }
    public SubmissionError(boolean didError) {
        this.errored = didError;
        this.error = null;
        this.niceError = null;
        this.hasErrorMessage = false;
    }

    public boolean succeed() {
        return !errored;
    }
    public boolean errored() {
        return errored;
    }
}