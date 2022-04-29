package client_project.y2s1.team2.graphium.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ReturnError {
    @Getter
    private final boolean errored;        // Did it not pass or succeed
    @Getter
    private final String error;           // It's short error, maybe the main title to check against
    @Getter
    private final String niceError;       // It's longer error message to explain the issue, possibly display to users

    private final boolean hasErrorMessage;


    public ReturnError(boolean didError, String aError, String aNiceError) {
        this.errored = didError;
        this.error = aError;
        this.niceError = aNiceError;
        this.hasErrorMessage = true;
    }
    public ReturnError(boolean didError, String aError) {
        this.errored = didError;
        this.error = aError;
        this.niceError = aError;
        this.hasErrorMessage = true;
    }
    public ReturnError(boolean didError) {
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
