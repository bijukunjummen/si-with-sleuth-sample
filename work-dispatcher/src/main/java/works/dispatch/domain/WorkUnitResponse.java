package works.dispatch.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WorkUnitResponse {
    private final String id;
    private final String definition;
    private final boolean success;

    @JsonCreator
    public WorkUnitResponse(@JsonProperty("id") String id,
                            @JsonProperty("definition") String definition,
                            @JsonProperty("success") boolean success) {
        this.id = id;
        this.definition = definition;
        this.success = success;
    }

    public String getId() {
        return id;
    }


    public String getDefinition() {
        return definition;
    }

    public boolean isSuccess() {
        return success;
    }
}
