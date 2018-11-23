package ua.edu.ratos.service.session.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Optional;

@Getter
@Setter
@Accessors(chain = true)
public class Help {

    private Long helpId;

    private String name;

    private String help;

    private Resource resource;

    // Resource may or may not be present, most often it is not present
    @JsonIgnore
    public Optional<Resource> getResource() {
        return Optional.ofNullable(resource);
    }

    @JsonProperty("resource")
    private Resource getResourceOrNothing() {
        return getResource().orElse(null);
    }

}
